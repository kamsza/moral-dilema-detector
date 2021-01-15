package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.links.Link;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numLinks",
        "link"
})
public class Links {
    @JsonProperty("numLinks")
    public Integer numLinks;
    @JsonProperty("link")
    public Link link;
}