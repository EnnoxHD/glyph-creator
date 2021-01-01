package com.github.ennoxhd.glyphcreator.util.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Contains some utility functions related to regular expressions.
 */
public class RegexUtils {

	/**
	 * Searches for a regex pattern in a provided value.
	 * While searching it stores a list for each occurrence
	 * where the named regex groups and their corresponding values are mapped.
	 * @param pattern regex pattern to search for
	 * @param value the value to search in
	 * @return a list of all occurrences with a mapping of the named regex groups and their value
	 */
	public static List<Map<String, String>> getOccurrences(String pattern, String value) {
		final String groupNameToken = "groupname";
		final String groupNamePattern = "\\(\\?<(?<" + groupNameToken + ">\\w+)>";
		if(pattern.equals(value) && pattern.equals(groupNamePattern)) {
			List<Map<String, String>> identity = new ArrayList<>();
			Map<String, String> identityOccurrence = new HashMap<>();
			identityOccurrence.put(groupNameToken, groupNameToken);
			identity.add(identityOccurrence);
			return identity;
		}
		// recursive call
		List<Map<String, String>> groupNameOccurrences = getOccurrences(groupNamePattern, pattern);
		Set<String> availableGroupNames = groupNameOccurrences.parallelStream()
				.map(m -> m.get(groupNameToken))
				.filter(s -> s != null && !s.isBlank())
				.collect(Collectors.toSet());
		List<Map<String, String>> result = new ArrayList<>();
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(value);
		while(matcher.find()) {
			Map<String, String> resultMap = new HashMap<>();
			for(String availableGroupName : availableGroupNames) {
				try {
					resultMap.put(availableGroupName, matcher.group(availableGroupName));
				} catch(IllegalArgumentException e) {
					resultMap.put(availableGroupName, null);
				}
			}
			result.add(resultMap);
		}
		return result;
	}
}
