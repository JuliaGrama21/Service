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
                                        <li><a href="/admin_page">Платежі всіх користувачів</a></li>
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
        console.log(this.props.payments);
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
                                <td>{payment.userName}</td>
                                <td>{payment.type}</td>
                                <td>{payment.amount}</td>
                                <td>{payment.organizationName}</td>
                                <td>{payment.time}</td>
                                <td>{payment.paid ? "Оплачено" : "Не Оплачено"} </td>
                            </tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    }
}

class Admin extends React.Component {
    tabs = [
        {text: 'Платежі всіх користувачів', link: '/admin_page'},
        {text: 'Тарифи', link: '/tariff'}
    ];

    columns = [
        "Користувач", "Тип", "Сума", "Організація", "Дата", "Стан"
    ];

    state = {
        payments: [],
        userName: "",
    };

    constructor() {
        super();
        fetch('/allPayments')
            .then(response => response.json())
            .then(data => this.setState({ payments: data }));
        fetch('/user')
            .then(response => response.json())
            .then(data => {this.setState({ userName: data.userName })});
    }

    render() {
        console.log(this.state.payments);
        return (
            <div className="cabinet">
                <Header
                    title="Кабінет адміністратора"
                    welcomeText="Доброго дня"
                    userName={this.state.userName}
                    tabs={this.tabs}/>
                <Content
                    columns={this.columns}
                    payments={this.state.payments}
                    getTime={this.state.time}
                />
            </div>
        );
    }
}


const rootContainer = document.getElementById('root');
ReactDOM.render(<Admin/>, rootContainer);