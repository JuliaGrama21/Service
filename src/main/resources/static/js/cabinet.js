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

class Content extends React.Component {

    render() {
        return <div>
        <div className="cabinet-content">
        <div className="table">
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
                {this.props.payments.map(payment =>
                        <tr>
                            <td>{payment.type}</td>
                            <td>{payment.amount}</td>
                            <td>{payment.organizationName}</td>
                            <td>{payment.time}</td>
                            <td>{payment.paid ? "Оплачено" : <button onClick={() => this.props.onPayPayment(payment.id)}>Оплатити</button>} </td>
                            <td>{<button onClick={() => this.props.onDeletePayment(payment.id)}>Видалити</button>} </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
        </div>
        </div>
    }
}

class PersonalCabinet extends React.Component {
    tabs = [
        {text: 'Мої платежі', link: '/personal_cabinet'},
        {text: 'Створення платежу', link: '/createPayTemplate'},
        {text: 'Звітність', link: '/report_page'}
    ];

    columns = [
        "Тип", "Сума", "Організація", "Дата", "Стан"
    ];

    state = {
        payments: [],
        userName: "",
        time: ""
    };

    constructor() {
        super();
        fetch('/userPayments')
            .then(response => response.json())
            .then(data => this.setState({ payments: data }));
        fetch('/user')
            .then(response => response.json())
            .then(data => {this.setState({ userName: data.userName })});
    }

    payPayment= paymentId => {
        const that = this;
        fetch('/payPayment', {
            body: JSON.parse( paymentId),
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            referrer: 'no-referrer',
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                that.setState({payments: myJson});
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    deletePayment= paymentId => {
        const that = this;
        fetch('/deletePayment', {
            body: JSON.parse( paymentId),
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            referrer: 'no-referrer',
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                that.setState({payments: myJson});
            })
            .catch(function(error) {
                console.log(error);
            });
    };

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
                    payments={this.state.payments}
                    getTime={this.state.time}
                    onPayPayment={this.payPayment}
                    onDeletePayment={this.deletePayment}
                />
            </div>
        );
    }
}


const rootContainer = document.getElementById('root');
ReactDOM.render(<PersonalCabinet/>, rootContainer);