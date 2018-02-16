'use strict';

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
        this.url = url;
        return this;
    }

    addResource(resource) {
        this.path = this.path + "/" + resource;
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
        this.data.clear();
        this.pathIsBuilt = false;
        return this;
    }

    perform(method, dataType, success = function (data) {}, error = function(data) {}) {
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
            dataType: dataType,
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

function loadToken(func = function (out) {}) {
    let token = localStorage.getItem("token");
    token = (token == null) ? "" : token;
    let request = new RequestBuilder();
    request.addUrl("http://localhost:8080")
        .addResource("check")
        .addValues("t", token)
        .buildRequest();
    request.perform("GET", "text", function (out) {
        switch (out) {
            case "22":
                localStorage.removeItem("t");
                security.token = "";
                break;
            default:
                security.token = token;
                break;
        }
        func(security.token);
    });
}

class RestSecurity {
    constructor() {
        this.data = null;
        this.token = "";
    }

    loadToken(func = function (out) {}) {
        loadToken(func);
    }

    addData(data) {
        this.data = data;
    }

    perform(tokenLocalSavingIsEnabled,
            oSD = function (out) {},
            oED = function (out) {}) {
        let obj = this;
        this.data.perform("POST",
            "text",
            function (data) {
                if (data !== "20") {
                    saveToken(data, tokenLocalSavingIsEnabled, obj);
                    oSD(data);
                }
            },
            function (data) {
                oED(data);
            });

        /*let url = this.authURL;
        let credits = this.credentials.format();
        $.ajax({
            type: "POST",
            url: url,
            data: credits,
            dataType: "text",
            success: function (msg) {
                if (msg !== "20") {
                    obj::saveToken(msg, tokenLocalSavingIsEnabled);
                    outputDelegate(msg);
                }
            },
            error: function (msg) {
                switch (msg) {
                    case "30" :
                        console.log("Invalid credentials were passed");
                        break;
                }
            }
        });*/
    }

    getToken() {
        return this.token;
    }
}
