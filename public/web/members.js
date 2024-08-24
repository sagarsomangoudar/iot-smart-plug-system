/**
 * A model for managing members in groups.
 */
function create_members_model(groups) {
	// create the data structure
	var all_members = new Set(); // all unique member names
	var group_names = [];
	var group_members = new Map(); // group_name to set of group members 
	for (var group of groups) {
		group_names.push(group.name);
		
      	var members = group.members.map(member => ({ 
            name: member.name, 
            state: member.state, 
            power: member.power
        })); 

		group_members.set(group.name, members);
		members.forEach(member => all_members.add(member.name));
	}

	var member_names = Array.from(all_members);
	group_names.sort();
	member_names.sort();

	// create the object
	var that = {}
	that.get_group_names = () => group_names;
	that.get_member_names = () => member_names;

    that.is_member_in_group = (member_name, group_name) =>
    group_members.has(group_name) && group_members.get(group_name).some(member => member.name === member_name);

	
	that.get_group_members = group_name => group_members.get(group_name);

	console.debug("Members Model",
		groups, group_names, member_names, group_members);

	return that;
}

/**
 * The Members controller holds the state of groups.
 * It creates its view in render().
 */
class Members extends React.Component {

	constructor(props) {
		super(props);
		console.info("Members constructor()");
		this.state = {
			members: create_members_model([]),
			inputName: "",
			inputMembers: "",
		};
	}

	componentDidMount() {
		console.info("Members componentDidMount()");
		this.getGroups();
		setInterval(this.getGroups, 1000);
	}

	render() {
		console.info("Members render()");
		return (<MembersTable members={this.state.members}
			inputName={this.state.inputName} inputMembers={this.state.inputMembers}
			onMemberChange={this.onMemberChange}
			onDeleteGroup={this.onDeleteGroup}
			onInputNameChange={this.onInputNameChange}
			onInputMembersChange={this.onInputMembersChange}
			onAddGroup={this.onAddGroup}
			onAddAllMembers={this.onAddAllMembers}
			onAddMemberToAllGroups={this.onAddMemberToAllGroups} 
			onDeleteMemberFromAllGroups={this.onDeleteMemberFromAllGroups}/>);
	}

	getGroups = () => {
		console.debug("RESTful: get groups");
		fetch("api/groups")
			.then(rsp => rsp.json())
			.then(groups => this.showGroups(groups))
			.catch(err => console.error("Members:", err));
	}

	showGroups = groups => {
		this.setState({
			members: create_members_model(groups)
		});
	}

	createGroup = (groupName, groupMembers) => {
		console.info("RESTful: create group "+groupName
			+" "+JSON.stringify(groupMembers));
		
		var postReq = {
			method: "POST",
			headers: {"Content-Type": "application/json"},
			body: JSON.stringify(groupMembers)
		};
		console.log("POSTED", JSON.stringify(groupMembers)); 
		fetch("api/groups/"+groupName, postReq)
			.then(rsp => this.getGroups())
			.catch(err => console.error("Members: createGroup", err));
	}

	createManyGroups = groups => {
		console.info("RESTful: create many groups "+JSON.stringify(groups));
		var pendingReqs = groups.map(group => {

			var listOfMems = group.members;
			const membersArray = Object.values(listOfMems);
			var memberNames = membersArray.map(user => user.name);

			var postReq = {
				method: "POST",
				headers: {"Content-Type": "application/json"},
				body: JSON.stringify(memberNames)
			};
			return fetch("api/groups/"+group.name, postReq);
		});

		Promise.all(pendingReqs)
			.then(() => this.getGroups())
			.catch(err => console.error("Members: createManyGroup", err));
	}

	deleteGroup = groupName => {
		console.info("RESTful: delete group "+groupName);
	
		var delReq = {
			method: "DELETE"
		};
		fetch("api/groups/"+groupName, delReq)
			.then(rsp => this.getGroups())
			.catch(err => console.error("Members: deleteGroup", err));
	}

	onMemberChange = (memberName, groupName) => {
		
		var groupMemberObjects = this.state.members.get_group_members(groupName) || [];
		var groupMemberNames = new Set(groupMemberObjects.map(member => member.name));

		var newObject = { name: memberName, state: "off", power: "0.00" };

		if (groupMemberNames.has(memberName)) {
			groupMemberObjects = groupMemberObjects.filter(member => member.name !== memberName);	// If exists, filter out this member from the array
		} else {
			groupMemberObjects.push(newObject);        // If not exists, push the new object into the array
		}

		const memberNames = groupMemberObjects.map(member => member.name);
		this.createGroup(groupName, memberNames);
	}

	onDeleteGroup = groupName => {
		this.deleteGroup(groupName);
	}

	onInputNameChange = value => {
		console.debug("Members: onInputNameChange", value);
		this.setState({inputName: value});
	}

	onInputMembersChange = value => {
		console.debug("Members: onInputMembersChange", value);
		this.setState({inputMembers: value});
	}

	onAddGroup = () => {
		var name = this.state.inputName;
		var members = this.state.inputMembers.split(',');
	
		this.createGroup(name, members);
	}

	onAddMemberToAllGroups = memberName => {
		var groups = [];
		for (var groupName of this.state.members.get_group_names()) { 
			var groupMembers = this.state.members.get_group_members(groupName);

			groupMembers.push({name: memberName, state: "off", power:"0.00"});
			groups.push({name: groupName, members: groupMembers});
		}
		this.createManyGroups(groups);
	}

	onAddAllMembers = groupName => {

		var allMemberNames = this.state.members.get_member_names()		// This is where all names will be collected		
		console.log( "All members added to the group ", groupName, " members are: ", allMemberNames,);	

		allMemberNames = [...new Set(allMemberNames)];		//Remove duplicate names
		this.createGroup(groupName, allMemberNames);
	}

	onDeleteMemberFromAllGroups = memberName => {
		var groups = [];
    	for (var groupName of this.state.members.get_group_names()) {
     
        	var groupMemberObjects = this.state.members.get_group_members(groupName);   // Get the current list of member objects for the group
        
        	var updatedGroupMembers = groupMemberObjects.filter(member => member.name !== memberName);	 // Filter out the member object with the given memberName
        
        	groups.push({name: groupName, members: updatedGroupMembers});	// Prepare the updated group data for the request
    	}
 
    	this.createManyGroups(groups);   // Call a function to update all groups with the member removed
	}

}

// export
window.Members = Members;