package vetweb.store.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PriceRange {
	@JsonProperty("Zero to hundred")
	ZEROTOHUNDRED,
	@JsonProperty("Hundred to five hundreds")
	HUNDREDTOFIVEHUNDREDS,
	@JsonProperty("Five hundreds to thousand")
	FIVEHUNDREDSTOTHOUSAND,
	@JsonProperty("Thousand and above")
	THOUSANDANDABOVE,
	@JsonProperty("Unknown")
	UNKNOWN
	
}
