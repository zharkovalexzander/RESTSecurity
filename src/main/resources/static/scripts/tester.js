let mainText = "Check out a new opportunity to discover the world. Just log in for the journey!";
let security = null;

class LogoutButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.onClick = this.onClick.bind(this);
    }

    onClick(event) {
        event.preventDefault();
        let request = new RequestBuilder();
        request.addUrl("http://localhost:8080")
            .addResource("rm")
            .addValues("t", security.getToken())
            .buildRequest();
        request.perform(Method.GET, function (data) {
            switch (data) {
                case "30":
                    console.log("Invalid token. Please restart your session.");
                    break;
                case "21":
                    console.log("Session is removed");
                    break;

            }
            ReactDOM.render(
                <AuthForm/>,
                document.getElementById('root')
            );
        });
    }

    render() {
        return (
            <div className="logout" onClick={this.onClick}>Log out</div>
        )
    }
}

class AuthForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            code: ''
        };
        this.onNameChange = this.onNameChange.bind(this);
        this.onCodeChange = this.onCodeChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onNameChange(event) {
        this.setState({name: event.target.value, code: this.state.code});
    }

    onCodeChange(event) {
        this.setState({name: this.state.name, code: event.target.value});
    }

    onSubmit(event) {
        event.preventDefault();
        if (this.state.name && this.state.code) {
            let request = new RequestBuilder();
            request.addUrl("http://localhost:8080")
                .addResource("auth")
                .addValues("name", this.state.name)
                .addValues("code", this.state.code)
                .buildRequest();
            security.addData(request);
            security.perform(true, Method.POST, function (data) {
                if(data !== "30") {
                    renderCountries();
                }
            });
        }
    }

    componentDidMount() {
        $("#loader").remove();
    }

    render() {
        return (
            <div className='back' style={
                {
                    background: "url(\"static/media/172571-min.jpg\") no-repeat",
                    backgroundSize: "100% 100%"
                }
            }>
                <div className='fader'>
                    <div className='AuthView'>
                        <form onSubmit={this.onSubmit} className='AuthForm' noValidate="true">
                            <div className='i i_name' type='text'>
                                OPEN YOUR MIND
                            </div>
                            <div className='parag' type='text' dangerouslySetInnerHTML={{__html: mainText}}>
                            </div>
                            <input className='i inputs' type='text' placeholder='Name' value={this.state.name}
                                   onChange={this.onNameChange}/>
                            <input className='i inputs' type='text' placeholder='Code' value={this.state.code}
                                   onChange={this.onCodeChange}/>
                            <button className='button' type='submit'>
                                Log in
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

function renderCountries() {
    $("#loader").remove();
    let request = new RequestBuilder();
    request.addUrl("http://localhost:8080")
        .addResource("countries")
        .addValues("t", security.getToken())
        .buildRequest();
    request.perform(Method.POST, function (data) {
        let counties = JSON.parse(data).filter(country => country["url"] != null);
        let countriesList = [];

        class CountryContainer extends React.Component {
            constructor(props) {
                super(props);
            }

            render() {
                return countriesList;
            }
        }

        for (let i = 0; i < counties.length; ++i) {
            class CountryEntity extends React.Component {
                constructor(props) {
                    super(props);
                    this.onClick = this.onClick.bind(this);
                    this.state = {
                        background: counties[i]["url"],
                        name: counties[i]["name"],
                        code: counties[i]["code"],
                        pos: i
                    }
                }

                onClick() {
                    let request = new RequestBuilder();
                    request.addUrl("http://localhost:8080")
                        .addResource("visit")
                        .addValues("t", security.getToken())
                        .addValues("code", this.state.code)
                        .buildRequest();
                    let position = this.state.pos;
                    request.perform(Method.GET, function (data) {
                        if(data === "1") {
                            countriesList.splice(position, 1);
                            ReactDOM.render(
                                <CountryContainer/>,
                                document.getElementById('root')
                            )
                        }
                    });
                }

                render() {
                    return (
                        <div className="country"
                             style={
                                 {
                                     background: 'url("' + this.state.background
                                     + '") no-repeat 0px -25px / 100%'
                                 }
                             }>
                            <div className="but"
                                 onClick={this.onClick}
                                 dangerouslySetInnerHTML={
                                     {
                                         __html: "Visit <br/>" + this.state.name
                                     }
                                 }>
                            </div>
                        </div>
                    );
                }
            }

            countriesList.push(<CountryEntity/>);
        }
        countriesList.push(<LogoutButton/>);

        ReactDOM.render(
            <CountryContainer/>,
            document.getElementById('root')
        )
    });
}

$(document).ready(function() {
    security = new RestSecurity();
    let request = new RequestBuilder();
    request.addUrl("http://localhost:8080")
        .addResource("check");
    security.loadToken(request, Method.GET, function (out) {
        if(out === "") {
            ReactDOM.render(
                <AuthForm/>,
                document.getElementById('root')
            );
        } else {
            renderCountries();
        }
    });
});