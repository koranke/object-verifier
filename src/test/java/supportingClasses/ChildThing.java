package supportingClasses;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class ChildThing {
	private String callSign;
	private String quote;
	private long[] idHistory;
	private boolean isActive;
	private int age;
	private Long id;
	private LocalDateTime dateJoined;
	private List<String> favoriteWords;
	private List<Integer> favoriteNumbers;

	public String getCallSign() {
		return callSign;
	}

	public ChildThing setCallSign(String callSign) {
		this.callSign = callSign;
		return this;
	}

	public String getQuote() {
		return quote;
	}

	public ChildThing setQuote(String quote) {
		this.quote = quote;
		return this;
	}

	public long[] getIdHistory() {
		return idHistory;
	}

	public ChildThing setIdHistory(long[] idHistory) {
		this.idHistory = idHistory;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public ChildThing setActive(boolean active) {
		isActive = active;
		return this;
	}

	public int getAge() {
		return age;
	}

	public ChildThing setAge(int age) {
		this.age = age;
		return this;
	}

	public Long getId() {
		return id;
	}

	public ChildThing setId(Long id) {
		this.id = id;
		return this;
	}

	public LocalDateTime getDateJoined() {
		return dateJoined;
	}

	public ChildThing setDateJoined(LocalDateTime dateJoined) {
		this.dateJoined = dateJoined;
		return this;
	}

	public List<String> getFavoriteWords() {
		return favoriteWords;
	}

	public ChildThing setFavoriteWords(List<String> favoriteWords) {
		this.favoriteWords = favoriteWords;
		return this;
	}

	public List<Integer> getFavoriteNumbers() {
		return favoriteNumbers;
	}

	public ChildThing setFavoriteNumbers(List<Integer> favoriteNumbers) {
		this.favoriteNumbers = favoriteNumbers;
		return this;
	}

	public static ChildThing getPopulatedChildThing(long id) {
		ChildThing childThing = new ChildThing();
		childThing.age = 10;
		childThing.dateJoined = LocalDateTime.now();
		childThing.id = id;
		childThing.favoriteWords = Lists.newArrayList("beer", "whiskey");
		childThing.favoriteNumbers = Lists.newArrayList(2, 4, 12, 44);
		childThing.isActive = true;
		childThing.callSign = "TrueBlue";
		childThing.quote = "I invented the Internet.";
		childThing.idHistory = new long[] { 132412342, 23452345, 7890 };

		return childThing;
	}
}
