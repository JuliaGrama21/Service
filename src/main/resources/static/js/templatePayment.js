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
                                        <li><a href="/payment_page">Новий платіж</a></li>
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
                            </div>)}
                    </div>
                </div>
            </div>
        );
    }
}

class Content extends React.Component {
    state = {
        organizations: null,
    };

    fetchOrganizations(type) {
        fetch('/getOrganizations?type=' + type)
            .then(response => response.json())
            .then(data => this.setState({organizations: data}));
    };

    render() {
        return <div>
            <div className="cabinet-content">
                <div className="table">
                    <section>
                        <h3 className="myPayment">{this.props.selection}</h3>
                    </section>
                    <div>
                        <ul>
                            {this.props.services.map(service =>
                                <div className="service" key={service.name}>
                                    <li>
                                        <a onClick={() => this.fetchOrganizations(service.type)}>{service.name}</a>
                                    </li>
                                </div>
                            )}
                        </ul>
                    </div>
                    <OrganizationList organizations={this.state.organizations}/>
                </div>
            </div>
        </div>
    }
}

class TemplatePayment extends React.Component {
    tabs = [
        {text: 'Мої платежі', link: '/personal_cabinet'},
        {text: 'Створення платежу', link: '/createPayTemplate'},
        {text: 'Звітність', link: '/report_page'}
    ];

    services = [
        {name: 'Вода', type: 'WATER'},
        {name: 'Газ', type: 'GAS'},
        {name: 'Світло', type: 'ELECTRICITY'},
        {name: 'Інтернет', type: 'INTERNET'},
        {name: 'Телефон', type: 'HOME_PHONE'},
    ];

    state = {
        userName: "",
        organizations: []
    };

    constructor() {
        super();
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
                    tabs={this.tabs}
                />
                <Content
                    selection="Оберіть необхідну послугу для створення платежу"
                    services={this.services}
                />
            </div>
        );
    }
}

class OrganizationList extends React.Component {
    render() {
        return <div>
            <div className="organizations">
                {this.props.organizations && <h3>Оберіть організацію</h3>}
                <ul>
                    {this.props.organizations && this.props.organizations.map(organization =>
                        <div className="organization" key={organization.name}>
                            <li>
                                <a href={`/paymentsPage?id=${organization.id}`}>{organization.name}</a>
                            </li>
                        </div>
                    )}
                </ul>
            </div>
        </div>
    }
}

const rootContainer = document.getElementById('root');
ReactDOM.render(<TemplatePayment/>, rootContainer);
