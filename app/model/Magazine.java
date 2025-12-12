package app.model;

public class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(int id, String title, int issueNumber, int stock) {
        super(id, title, stock);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() { return issueNumber; }

    @Override
    public String getItemDetails() {
        return "Magazine [ID=" + getId() + ", Title=" + getTitle() + ", Issue=" + issueNumber + ", Stock=" + getStock() + "]";
    }
}
