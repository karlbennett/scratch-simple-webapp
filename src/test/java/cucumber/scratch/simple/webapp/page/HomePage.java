package cucumber.scratch.simple.webapp.page;

public interface HomePage {

    void visit();

    void clickRegister();

    void clickSignIn();

    boolean isCurrentPage();

    String getUsername();
}
