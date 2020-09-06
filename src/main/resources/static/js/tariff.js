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
                            </div>)}
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
        editingOrganizationId: '',
        errorTariff: '',
        tariff: '',
        organizationId: ''
    };

    changeTariff(tariff) {
        this.setState({tariff});
    }
    saveTariff = organizationId => {
        if (this.isTariffValid(this.state.tariff)) {
            fetch(`/newTariff?organizationId=${organizationId}`
                + `&tariff=${this.state.tariff}`)
                .then(response => response.json())
                .then(data => {
                    this.setState({ tariffs: data})
                });
        }
    };

    submit(event) {
        if (!this.isFormValid()) {
            event.preventDefault();
            return;
        }
    }

    isFormValid() {
        return this.isTariffValid(this.state.tariff);
    }

    isTariffValid(tariff) {
        let errorTariff = '';

        if (tariff === '') {
            errorTariff = 'Поле не може бути пустим';
            this.setState({errorTariff});
            return false;
        }

        if (tariff < 0) {
            errorTariff = 'Ціна не повинна бути від*ємною';
            this.setState({errorTariff});
            return false;
        }

        if (isNaN(parseFloat(tariff))) {
            errorTariff = 'Неправильний формат ціни';
            this.setState({errorTariff});
            return false;
        }

        this.setState({errorTariff});
        return true;

    }

    render() {
        return <div>

            <div className="cabinet-content">

                <div className="table">
                    <div>
                        <h3>Всі тарифи</h3>
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
                            {this.props.tariffs.map(tariff =>
                                <tr>
                                    <td>{tariff.name}</td>
                                    <td>{tariff.type}</td>
                                    <td>{tariff.tariff}</td>
                                    <td><button onClick={() => this.setState({editingOrganizationId: tariff.id})}>Змінити тариф</button></td>
                                    {this.state.editingOrganizationId === tariff.id ?
                                    <td  className="edit">
                                        <Input
                                        onChange={this.changeTariff.bind(this)}
                                        type="text"
                                        className="form-control3 field"
                                        name="tariff"
                                        error={this.state.errorTariff}
                                        />
                                    </td>:null}
                                    {this.state.editingOrganizationId === tariff.id ?
                                        <td><button type="submit" onClick={this.saveTariff.bind(this, tariff.id)}
                                                    className="form-control btn">Зберегти</button></td>:null}
                                </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    }
}

class Tariff extends React.Component {
    tabs = [
        {text: 'Платежі всіх користувачів', link: '/admin_page'},
        {text: 'Тарифи', link: '/tariff'}
    ];

    columns = [
        "Послуга", "Тариф"
    ];

    state = {
        tariffs: [],
        userName: ""
    };

    constructor() {
        super();
        fetch('/tariffs')
            .then(response => response.json())
            .then(data => this.setState({ tariffs: data }));
        fetch('/user')
            .then(response => response.json())
            .then(data => { this.setState({userName: data.userName}) });
    }

    render() {
        return (
            <div className="cabinet">
                <Header
                    title="Кабінет адміністратора"
                    welcomeText="Доброго дня"
                    userName={this.state.userName}
                    tabs={this.tabs}
                />
                <Content
                    columns={this.columns}
                    tariffs={this.state.tariffs}
                    fild3="Введіть новий тариф"
                />
            </div>
        );
    }
}

const rootContainer = document.getElementById('root');
ReactDOM.render(<Tariff/>, rootContainer);