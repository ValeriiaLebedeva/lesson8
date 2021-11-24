import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byPartialLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TabShouldBeVisibleAtRepoPageTest {

    private static final String TABNAME = "Actions";


    // Проверяем, что на страничках реп selenide и allure есть таба Actions
    @ValueSource(strings = {"selenide", "allure"})
    @Tag("Blocker")
    @ParameterizedTest(name = "Tab should be visible at {0} page")
    void tabShouldBeVisibleValueSource(String nameOfRepository) {
        open("https://github.com");
        $("[name=q]").setValue(nameOfRepository).pressEnter();
        $(byPartialLinkText(nameOfRepository)).click();
        $(byPartialLinkText(TABNAME)).shouldBe(visible);
    }

    // проверяем, что на странице репы selenide есть таба Actions, а на стр. репы allure таба Pull requests
    @CsvSource(value = {
            "selenide* Actions",
            "allure* Pull requests"
    },
            delimiter = '*')
    @Tag("Blocker")
    @ParameterizedTest(name = "{1} should be visible at {0} page")
    void tabShouldBeVisibleCsvSource(String nameOfRepository, String tabName) {
        open("https://github.com");
        $("[name=q]").setValue(nameOfRepository).pressEnter();
        $(byPartialLinkText(nameOfRepository)).click();
        $(byPartialLinkText(tabName)).shouldBe(visible);
    }

    // чекаем, что на страницах реп selenide и allure есть табы Actions и Pull requests
    static Stream<Arguments> tabShouldBeVisibleMethodSource() {
        return Stream.of(
                Arguments.of("selenide", List.of("Actions", "Pull requests")),
                Arguments.of("allure", List.of("Actions", "Pull requests"))
        );
    }

    @MethodSource
    @Tag("Blocker")
    @ParameterizedTest(name = "{1} tabs should be visible at {0} page")
    void tabShouldBeVisibleMethodSource(String nameOfRepository, List<String> tabName) {
        open("https://github.com");
        $("[name=q]").setValue(nameOfRepository).pressEnter();
        $(byPartialLinkText(nameOfRepository)).click();
        $(byPartialLinkText(tabName.get(0))).shouldBe(visible);
        $(byPartialLinkText(tabName.get(1))).shouldBe(visible);
    }

    // Проверяем, что на страничках реп selenide и allure есть таба Actions
    @EnumSource(RepoName.class)
    @Tag("Blocker")
    @ParameterizedTest(name = "Actions tab should be visible at {0} repo page")
    void tabShouldBeVisibleEnumSource(RepoName repoName) {
        open("https://github.com");
        $("[name=q]").setValue(String.valueOf(repoName)).pressEnter();
        $(byPartialLinkText(String.valueOf(repoName))).click();
        $(byPartialLinkText("Actions")).shouldBe(visible);
    }


}