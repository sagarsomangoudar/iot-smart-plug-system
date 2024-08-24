package ece448.iot_hub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupsResource {

	
	
	private GroupsModel groups;

	public GroupsResource(GroupsModel groups) {
		this.groups = groups;
	}
	
	@GetMapping("/api/groups")
	public Collection<Map<String, Object>> getGroups() throws Exception {
		ArrayList<Map<String, Object>> ret = new ArrayList<>();
		for (String group: groups.getGroups()) {
			ret.add(makeGroup(group));
		}
		logger.info("Groups: {}", ret);
		return ret;
	}

	@GetMapping("/api/groups/{group}")
	public Object getGroup(
		@PathVariable("group") String group,
		@RequestParam(value = "action", required = false) String action) throws Exception {
		if (action == null) {
			Map<String, Object> ret = makeGroup(group);
			logger.info("Group {}: {}", group, ret.get("A"));
		
			return ret;
		}

		List<Map<String, Object>> members = groups.getGroupMembers(group);
		logger.info("Group {}: action {}, {}", group, action, members);

		groups.publishGroup(group, action);

		Object ret = makeGroup(group);
		return ret;
	}

	@PostMapping("/api/groups/{group}")
	public void createGroup(
		@PathVariable("group") String group,
		@RequestBody List<String> members) {
		groups.setGroupMembers(group, members);
		logger.info("Group {}: created {}", group, members);
	}

	@DeleteMapping("/api/groups/{group}")
	public void removeGroup(
		@PathVariable("group") String group) {
		groups.removeGroup(group);
		logger.info("Group {}: removed", group);
	}

	protected Map<String, Object> makeGroup(String group) {
		// modify code below to include plug states

		Map<String, Object> ret = new HashMap<>();
		List<Map<String, Object>> members = groups.getGroupMembers(group);
		
		ret.put("name", group);
		ret.put("members", members);

		logger.info("Group details {}:", ret);
		return ret;
	}

	

	private static final Logger logger = LoggerFactory.getLogger(GroupsResource.class);	
}
