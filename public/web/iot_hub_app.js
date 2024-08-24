/**
 * The App class is a controller holding the global state.
 * It creates all children controllers in render().
 */
class IoTHubApp extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			plugSelected: null,
			showGroupTable: false,
		};
	}

	toggleGroupTable = () => {
        this.setState(prevState => ({
            showGroupTable: !prevState.showGroupTable
        }));
    }

	updatePlugSelected(plug) {
		this.setState({ plugSelected: plug });
	}

	render() {

		const buttonText = this.state.showGroupTable ? '← Go Back' : 'Manage Groups';
		const buttonStyle = this.state.showGroupTable ? "btn btn-secondary" : "btn btn-primary";
		return (
			<div className="container">
				<div className="row">
					<h3>Welcome to IoT Hub from ECE448/ECE528@IIT!</h3>
					
				</div>
				<div className="row">
				<hr className="col-sm-12" /> 
					<h4>
						<button className= {buttonStyle} onClick={this.toggleGroupTable}>{buttonText}</button>
					</h4>
				</div>

				{this.state.showGroupTable && <Members />}

				<div className="row">
					<hr className="col-sm-12" /> 
						<h4 style={{ color: 'blue', fontWeight: 'bold' }}>Plug List</h4>
				</div>

				<div className="row">
					<div className="col-sm-2">
						<Plugs
							updatePlugSelected={plug => this.updatePlugSelected(plug)}
							plugSelected={this.state.plugSelected} />
					</div>
					<div className="col-sm-10">
						<PlugDetails
							plugSelected={this.state.plugSelected} />
					</div>
				</div>
			</div>
		);
	}
}

window.IoTHubApp = IoTHubApp;
