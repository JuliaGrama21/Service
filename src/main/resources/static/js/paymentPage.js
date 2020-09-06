class Header extends React.Component {
    render() {
        return (
            <div>
                <div className="flex-container">
                    <div className="flex-left">
                        <img src="/css/payb/communal.png" className="logo"/>
                    </div>
                    <div className="flex-center"><h3>{this.props.title}</h3></div>

                    <div className="flex-right">
                        <div className="menu-wrapper">
                            <ul className="menu">
                                <li><a>{this.props.welcomeText}, {this.props.userName} </a>
                                    <ul>
                                        <li><a href="/personal_cabinet">Мої платежі</a></li>
                                        <li><a href="/template">Новий платіж</a></li>
                                        <li><a href="/logout">Вийти</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div className="tabs-container">
                    <div className="flex-center">
                        {this.props.tabs.map(tab =>
                            <div className="tab" key={tab.text}>
                                <a href={tab.link}>{tab.text}</a>
                            </div>)
                        }
                    </div>
                </div>
            </div>
        );
    }
}

class Input extends React.Component {

    constructor(props) {
        super(props);
        const {value} = this.props;
        this.state = {value};
    }

    handleChange(event) {
        const {value} = event.target;
        this.props.onChange(value);
        this.setState({value});
    }

    render() {
        return (
            <div className={`form-group ${!!this.props.error ? ' has-error' : null}`}>
                <input
                    type='text'
                    name={this.props.name}
                    value={ this.state.value}
                    onChange={this.handleChange.bind(this)}
                />
                {this.props.error ? <span className='help-block'>{this.props.error}</span> : null}
            </div>
        );
    }

}

class Content extends React.Component {

    state = {
        payments: [],
        paymentType: "",
        indicator: '',
        errorIndicator: '',
    };


    payPayment = paymentId => {
        const that = this;
        fetch('/payPayment?organizationId=' + new URLSearchParams(location.search).get('id'), {
            body: JSON.parse(paymentId),
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            referrer: 'no-referrer',
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (myJson) {
                that.setState({payments: myJson});
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    deletePayment = paymentId => {
        const that = this;
        fetch('/deletePayment?organizationId=' + new URLSearchParams(location.search).get('id'), {
            body: JSON.parse(paymentId),
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            referrer: 'no-referrer',
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (myJson) {
                that.setState({payments: myJson});
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    fetchUserPayments = organizationId => {
        fetch('/userPaymentsByOrganization?id=' + organizationId)
            .then(response => response.json())
            .then(data => this.setState({paymentType: data.organizationType, payments: data.payments}));
    };

    constructor() {
        super();
        const searchParams = new URLSearchParams(location.search);
        this.fetchUserPayments(searchParams.get('id'));
    }

    changeIndicator(indicator) {
        this.setState({indicator});
    }

    saveIndicator = () => {
        if (this.isIndicatorValid(this.state.indicator)) {
            fetch('/createPayment?organizationId=' + new URLSearchParams(location.search).get('id')
                + '&indicator=' + this.state.indicator)
                .then(response => response.json())
                .then(data => {
                    this.setState({ payments: data})
                });
        }
    };

    isIndicatorValid(indicator) {
        let errorIndicator = '';

        if (indicator === '') {
            errorIndicator = 'Поле не може бути пустим';
            this.setState({errorIndicator});
            return false;
        }

        if (isNaN(parseFloat(indicator))) {
            errorIndicator = 'Неправильний формат показника';
            this.setState({errorIndicator});
            return false;
        }

        if (indicator < 0) {
            errorIndicator = 'Показник не повинен бути від*ємним';
            this.setState({errorIndicator});
            return false;
        }

        if (indicator.length > 8) {
            errorIndicator = 'Довжина поля повинна бути не більше 8';
            this.setState({errorIndicator});
            return false;
        }

        this.setState({errorIndicator});
        return true;
    }


    render() {
        return <div>
            <div className="cabinet-content">
                <div className="paymentGas">
                    <div>
                        <h3>Всі платежі</h3>
                        <table className="table-col">
                            <colgroup>
                                <col style={{"background": "#C7DAF0"}}/>
                            </colgroup>
                            <tbody>
                            <tr>
                                {this.props.columns.map(column =>
                                    <th>{column}</th>)
                                }
                            </tr>
                            {this.state.payments.map(payment =>
                                <tr>
                                    <td>{payment.indicator}</td>
                                    <td>{payment.amount}</td>
                                    <td>{payment.time}</td>
                                    <td>{payment.paid ? "Оплачено" :
                                        <button onClick={() => this.payPayment(payment.id)}>
                                            Оплатити</button>} </td>
                                    <td>{<button onClick={() => this.deletePayment(payment.id)}>
                                        Видалити</button>} </td>
                                </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="payGas">
                    <div className="enterIndicator">
                        <section>
                            <h3 className="myPayment">{this.state.paymentType}</h3>
                        </section>
                        <div className="Indicators">
                            <div>
                                <div className="input-group">
                                    <label>Введіть показники</label>
                                    <Input
                                        onChange={this.changeIndicator.bind(this)}
                                        type="text"
                                        className="form-control2 field"
                                        name="indicator"
                                        error={this.state.errorIndicator}
                                    />
                                </div>

                                <div className="input-group form-group">
                                    <button type="button" onClick={this.saveIndicator.bind(this)}
                                            className="form-control btn">Зберегти</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    }
}

class PaymentPage extends React.Component {
    tabs = [
        {text: 'Мої платежі', link: '/personal_cabinet'},
        {text: 'Створення платежу', link: '/createPayTemplate'},
        {text: 'Звітність', link: '/report_page'}
    ];

    columns = [
        "Показник", "Сума", "Дата", "Стан"
    ];

    state = {
        userName: "",
    };

    constructor() {
        super();
        fetch('/user')
            .then(response => response.json())
            .then(data => { this.setState({userName: data.userName}) });
    }


    render() {
        return (
            <div className="cabinet">
                <Header
                    title="Особистий кабінет"
                    welcomeText="Доброго дня"
                    userName={this.state.userName}
                    tabs={this.tabs}/>
                <Content
                    columns={this.columns}
                />
            </div>
        );
    }
}


const rootContainer = document.getElementById('root');
ReactDOM.render(<PaymentPage/>, rootContainer);