'use strict';

let Method = Object.freeze({
    "POST": "POST",
    "GET": "GET",
    "PATCH": "PATCH",
    "DELETE": "DELETE",
    "PUT": "PUT"
});

class Pair {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }

    getKey() {
        return this.key;
    }

    getValue() {
        return this.value;
    }

    setValue(value) {
        this.value = value;
    }
}

class ListOfPairs {
    constructor() {
        this.list = [];
        this.size = 0;
    }

    add(key, value) {
        if (!(typeof key === 'string') && !(typeof value === 'string')) {
            throw "Wrong type: you should use a string values as key and value";
        } else if (this.isPresent(key)) {
            for (let i = 0; i < this.size; ++i) {
                if (this.list[i].getKey() === key) {
                    this.list[i].setValue(value);
                    return;
                }
            }
        } else {
            this.list[this.size++] = new Pair(key, value);
        }
    }

    get(key) {
        if (this.isPresent(key)) {
            for (let i = 0; i < this.size; ++i) {
                if (this.list[i].getKey() === key) {
                    this.list[i].getValue();
                }
            }
        }
    }

    merge(listPairs) {
        for(let i = 0; i < listPairs.size; ++i) {
            if(!this.isPresent(listPairs.list[i].getKey())) {
                this.list[this.size++] = new Pair(listPairs.list[i].getKey(),
                    listPairs.list[i].getValue());
            }
        }
    }

    length() {
        return this.size;
    }

    isPresent(key) {
        for (let i = 0; i < this.size; ++i) {
            if (this.list[i].getKey() === key) {
                return true;
            }
        }
        return false;
    }

    clear() {
        this.size = 0;
        this.list = [];
    }

    format() {
        let query = "";
        for (let i = 0; i < this.size; ++i) {
            let pair = this.list[i];
            query += pair.getKey() + "=" + pair.getValue();
            if (i < (this.size - 1)) {
                query += "&"
            }
        }
        return query;
    }
}

class RequestBuilder {
    constructor() {
        this.url = "";
        this.path = "";
        this.data = new ListOfPairs();
        this.pathIsBuilt = false;
    }

    addUrl(url) {
        if(!RequestBuilder.validateUrl(url)) {
            throw "Url is not valid! Try again, please.";
        }
        this.url = url;
        return this;
    }

    addResource(resource) {
        let pth = resource.replace(/[^a-zA-Z0-9]/g, '');
        this.path = this.path + "/" + pth;
        return this;
    }

    buildRequest() {
        this.pathIsBuilt = true;
        return this;
    }

    addValues(key, value) {
        this.data.add(key, value);
        return this;
    }

    clear() {
        this.url = "";
        this.path = "";
        return this.clearData();
    }

    clearData() {
        this.data.clear();
        this.pathIsBuilt = false;
        return this;
    }

    static validateUrl(value) {
        return /(?:^|[ \t])((https?:\/\/)?(?:localhost|[\w-]+(?:\.[\w-]+)+)(:\d+)?)$/gm
            .test(value);
    }

    perform(method, success = function (data) {}, error = function(data) {}) {
        if(!this.pathIsBuilt) {
            console.log("Class wasn`t built!");
            return;
        }
        let path = this.url + this.path;
        let creds = this.data.format();
        $.ajax({
            type: method,
            url: path,
            data: creds,
            dataType: "text",
            success: function (msg) {
                success(msg);
            },
            error: function (msg) {
                error(msg);
            }
        });
    }
}

function saveToken(token, tokenLocalSavingIsEnabled, security) {
    security.token = token;
    if (tokenLocalSavingIsEnabled) {
        localStorage.setItem("token", token);
    }
}

function loadToken(url, method, object, func = function (out) {}) {
    let token = localStorage.getItem("token");
    token = (token == null) ? "" : token;
    url.clearData()
        .addValues("t", token)
        .buildRequest();
    url.perform(method, function (out) {
        if (out === "22") {
            localStorage.removeItem("t");
            object.token = "";
        } else {
            object.token = token;
        }
        func(object.token);
    });
}

class RestSecurity {
    constructor() {
        this.data = null;
        this.token = "";
    }

    loadToken(url, method, func = function (out) {}) {
        loadToken(url, method, this, func);
    }

    addData(data) {
        this.data = data;
    }

    perform(tokenLocalSavingIsEnabled,
            method,
            oSD = function (out) {},
            oED = function (out) {}) {
        let obj = this;
        this.data.perform(method,
            function (data) {
                if (data !== "20") {
                    saveToken(data, tokenLocalSavingIsEnabled, obj);
                    oSD(data);
                }
            },
            function (data) {
                oED(data);
            });
    }

    getToken() {
        return this.token;
    }
}
