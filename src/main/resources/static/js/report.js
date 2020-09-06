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

    TYPES = [
        'GAS', 'WATER', 'ELECTRICITY'
    ];

    MONTHS = [
        {key: 0, name: 'Січень'},
        {key: 1, name: 'Лютий'},
        {key: 2, name: 'Березень'},
        {key: 3, name: 'Квітень'},
        {key: 4, name: 'Травень'},
        {key: 5, name: 'Червень'},
        {key: 6, name: 'Липень'},
        {key: 7, name: 'Серпень'},
        {key: 8, name: 'Вересень'},
        {key: 9, name: 'Жовтень'},
        {key: 10, name: 'Листопад'},
        {key: 11, name: 'Грудень'},
    ];

    getPaymentsByMonth = (payments, month) => {
        return payments.filter(payment => {
            const date = new Date(payment.time);
            return date.getMonth() === month;
        });
    };

    getPaymentsByType = (payments, type) => {
        return payments.filter(payment => payment.type === type);
    };

    getOverallAmountForPayments = payments => {
        let overallAmount = 0;
        payments.forEach(payment => overallAmount += payment.amount);
        return overallAmount;
    };


    render() {
        return <div>
            <div className="cabinet-content">
                <div className="table">
                    <h3>Звітність по місяцях</h3>
                    <table className="table-col">
                        <colgroup>
                            <col style={{"background": "#C7DAF0"}}/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>Послуга</th>
                            {this.MONTHS.map(month =>
                                <th>{month.name}</th>)
                            }
                        </tr>
                        {this.TYPES.map(type => {
                            const paymentsByType = this.getPaymentsByType(this.props.payments, type);
                            return (<tr>
                                <td>{type}</td>
                                {this.MONTHS.map(month => {
                                    const paymentsByMonth = this.getPaymentsByMonth(paymentsByType, month.key);
                                    const overallAmount = this.getOverallAmountForPayments(paymentsByMonth);
                                    return <td>{overallAmount === 0 ? '' : overallAmount}</td>})
                                }
                            </tr>)
                        })}
                        <tr>
                            <td>Сума</td>
                            {this.MONTHS.map(month => {
                                const paymentsByMonth = this.getPaymentsByMonth(this.props.payments, month.key);
                                const overallAmount = this.getOverallAmountForPayments(paymentsByMonth);
                                return <td>{overallAmount === 0 ? '' : overallAmount}</td>})
                            }
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    }
}

class Report extends React.Component {
    tabs = [
        {text: 'Мої платежі', link: '/personal_cabinet'},
        {text: 'Створення платежу', link: '/createPayTemplate'},
        {text: 'Звітність', link: '/report_page'}
    ];

    state = {
        payments: [],
        userName: ""
    };


    constructor() {
        super();
        fetch('/paidUserPayments')
            .then(response => response.json())
            .then(data => this.setState({payments: data}));
        fetch('/user')
            .then(response => response.json())
            .then(data => {
                this.setState({userName: data.userName})
            });
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
                    payments={this.state.payments}
                    getTime={this.state.time}
                />
            </div>
        );
    }
}


const rootContainer = document.getElementById('root');
ReactDOM.render(<Report/>, rootContainer);