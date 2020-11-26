package wolox.training.client.models;

public class BooksParam {

    private String bibkeys;
    private String format = "json";
    private String jscmd = "data";

    public BooksParam() {
    }

    public BooksParam(String bibkeys) {
        this.bibkeys = bibkeys;
    }

    public String getBibkeys() {
        return bibkeys;
    }

    public void setBibkeys(String bibkeys) {
        this.bibkeys = bibkeys;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getJscmd() {
        return jscmd;
    }

    public void setJscmd(String jscmd) {
        this.jscmd = jscmd;
    }
}
