package supportingClasses;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.*;

public class ParentThing {
	private String firstName;
	private String lastName;
	private boolean isActive;
	private int age;
	private Long id;
	private LocalDateTime dateJoined;
	private List<String> favoriteWords;
	private List<Integer> favoriteNumbers;
	private String[] favoriteColors;
	private Set<String> aliases;
	private Map<Integer, String> codes;
	private List<ChildThing> childThings;
	private ChildThing specialChild;

	public String getFirstName() {
		return firstName;
	}

	public ParentThing setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public ParentThing setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public ParentThing setActive(boolean active) {
		isActive = active;
		return this;
	}

	public int getAge() {
		return age;
	}

	public ParentThing setAge(int age) {
		this.age = age;
		return this;
	}

	public Long getId() {
		return id;
	}

	public ParentThing setId(Long id) {
		this.id = id;
		return this;
	}

	public LocalDateTime getDateJoined() {
		return dateJoined;
	}

	public ParentThing setDateJoined(LocalDateTime dateJoined) {
		this.dateJoined = dateJoined;
		return this;
	}

	public List<String> getFavoriteWords() {
		return favoriteWords;
	}

	public ParentThing setFavoriteWords(List<String> favoriteWords) {
		this.favoriteWords = favoriteWords;
		return this;
	}

	public List<Integer> getFavoriteNumbers() {
		return favoriteNumbers;
	}

	public ParentThing setFavoriteNumbers(List<Integer> favoriteNumbers) {
		this.favoriteNumbers = favoriteNumbers;
		return this;
	}

	public String[] getFavoriteColors() {
		return favoriteColors;
	}

	public ParentThing setFavoriteColors(String[] favoriteColors) {
		this.favoriteColors = favoriteColors;
		return this;
	}

	public Set<String> getAliases() {
		return aliases;
	}

	public ParentThing setAliases(Set<String> aliases) {
		this.aliases = aliases;
		return this;
	}

	public Map<Integer, String> getCodes() {
		return codes;
	}

	public ParentThing setCodes(Map<Integer, String> codes) {
		this.codes = codes;
		return this;
	}

	public List<ChildThing> getChildThings() {
		return childThings;
	}

	public ParentThing setChildThings(List<ChildThing> childThings) {
		this.childThings = childThings;
		return this;
	}

	public ChildThing getSpecialChild() {
		return specialChild;
	}

	public ParentThing setSpecialChild(ChildThing specialChild) {
		this.specialChild = specialChild;
		return this;
	}

	public static ParentThing getPopulatedParent() {
		ParentThing parentThing = new ParentThing();
		parentThing.age = 40;
		parentThing.dateJoined = LocalDateTime.now();
		parentThing.id = 546987034L;
		parentThing.aliases = new HashSet<>(Lists.newArrayList("John","Johnson","Jon"));
		parentThing.favoriteColors = new String[] { "red", "blue", "green", "yellow"};
		parentThing.favoriteWords = Lists.newArrayList("cellar", "shell");
		parentThing.favoriteNumbers = Lists.newArrayList(10, 7, 112, 444);
		parentThing.firstName = "Joe";
		parentThing.lastName = "Jackson";
		parentThing.isActive = true;

		Map<Integer, String> codes = new HashMap<>();
		codes.put(1, "one");
		codes.put(5, "five");
		codes.put(8, "eight");

		parentThing.codes = codes;
		parentThing.childThings = new ArrayList<>();
		parentThing.childThings.add(ChildThing.getPopulatedChildThing(22));
		parentThing.childThings.add(ChildThing.getPopulatedChildThing(33));

		parentThing.specialChild = ChildThing.getPopulatedChildThing(54);
		return parentThing;
	}
}
