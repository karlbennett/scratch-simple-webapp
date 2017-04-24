package cucumber.scratch.simple.webapp.page;

public interface HomePage {

    void visit();

    boolean isSignedIn(String username);

    boolean isSignedOut();

    void clickRegister();

    void clickSignIn();

    void clickSignOut();

    boolean isCurrentPage();

    String getUsername();
}
