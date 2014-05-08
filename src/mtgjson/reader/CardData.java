/**
 *
 */
package mtgjson.reader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.lang3.text.WordUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class CardData {

    private final JsonObject jsonCard;

    private String cardName;
    private String infoUrl;
    private String imageUrl;
    private String rarity;
    private String manaCost;
    private String type;
    private String subType      = null;
    private String power        = null;
    private String toughness    = null;
    private String text         = null;

    public CardData(final JsonObject card) throws UnsupportedEncodingException {

        this.jsonCard = card;

        setCardName(card.get("name").getAsString());

        setInfoUrl("http://magiccards.info/query?q=%21" + URLEncoder.encode(card.get("name").getAsString(), "UTF-8"));
        setImageUrl("http://mtgimage.com/card/" + URLEncoder.encode(card.get("imageName").getAsString(), "UTF-8").replace("+", "%20") + ".jpg");
        setRarity(card.get("rarity").getAsString().substring(0,  1) + " #" + card.get("rarity").getAsString());

        if (card.has("manaCost"))   { setManaCost(card.get("manaCost").getAsString()); }
        if (card.has("power"))      { setPower(card.get("power").getAsString()); }
        if (card.has("toughness"))  { setToughness(card.get("toughness").getAsString()); }
        if (card.has("text"))       { setText(card.get("text").getAsString()); }

        if (card.has("types")) {
            final StringBuffer sb = new StringBuffer();
            final JsonArray cardTypes = card.getAsJsonArray("types");
            for (int j = 0; j < cardTypes.size(); j++) {
                sb.append(cardTypes.get(j).toString().replace("\"", "")).append(",");
            }
            final String typeValues = sb.toString().substring(0, sb.toString().length() - 1);
            setType(typeValues);
        }

        if (card.has("subtypes")) {
            final StringBuffer sb = new StringBuffer();
            final JsonArray cardTypes = card.getAsJsonArray("subtypes");
            for (int j = 0; j < cardTypes.size(); j++) {
                final String subType = cardTypes.get(j).toString();
                sb.append(subType
                            .replace("\"", "")
                            .replace(" ", "_")
                            .replace("'s", "")
                            .replace("�s", "")
                            .replace("-", "_")).append(",");
            }
            final String typeValues = sb.toString().substring(0, sb.toString().length() - 1);
            setSubType(typeValues);
        }

    }

    public String getCardName(final boolean replaceNonAscii) {
        if (replaceNonAscii) {
            return cardName.replace("�", "AE");
        } else {
            return cardName;
        }
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getInfoUrl() {
        return infoUrl;
    }
    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getManaCost() {
        return manaCost;
    }
    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        String filename = null;
        if (jsonCard.has("imageName")) {
            filename = jsonCard.get("imageName").getAsString();
        } else {
            filename = getCardName(true);
        }
        return WordUtils.capitalize(filename).replace(" ", "_") + ".txt";
    }

    public String getPower() {
        return power;
    }
    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }
    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getSubType() {
        return subType;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }

}
