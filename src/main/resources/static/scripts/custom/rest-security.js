'use strict';

class Pair {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }

    static createPair(key, value) {
        return new Pair(key, value);
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

class RestSecurity {
    constructor(authURL) {
        this.credentials = new ListOfPairs();
        this.authURL = authURL;
        this.token = "";
    }

    addCredits(key, value) {
        this.credentials.add(key, value);
    }

    saveToken(token, tokenLocalSavingIsEnabled) {
        this.token = token;
        if (tokenLocalSavingIsEnabled) {
            localStorage.setItem("token", token);
        }
    }

    perform(tokenLocalSavingIsEnabled, outputDelegate) {
        let url = this.authURL;
        let credits = this.credentials.format();
        let local_token = "";
        let obj = this;
        $.ajax({
            type: "POST",
            url: url,
            data: credits,
            dataType: "text",
            success: function (msg) {
                if (msg !== "20") {
                    obj.saveToken(msg, tokenLocalSavingIsEnabled);
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
        });
    }

    getToken() {
        return this.token;
    }

}