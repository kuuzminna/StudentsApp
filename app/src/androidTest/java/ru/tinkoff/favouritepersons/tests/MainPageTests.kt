package ru.tinkoff.favouritepersons.tests

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import io.qameta.allure.kotlin.AllureId
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import ru.tinkoff.favouritepersons.PersonDescription
import ru.tinkoff.favouritepersons.pages.MainPage
import ru.tinkoff.favouritepersons.utils.fileToString

class MainPageTests : BaseTest() {

    @Test
    @AllureId("1")
    @DisplayName("Проверка скрытия сообщения об отсутствии студентов")
    fun checkHidingMessageAboutAbsenceOfStudents() = run {
        stubFor(
            get(urlEqualTo("/api/"))
                .willReturn(ok(fileToString("mock/mock-first.json")))
        )
        MainPage {
            checkTextIsDisplayed()
            clickButtonAddPerson()
            clickButtonNetwork()
            checkTextIsNotDisplayed()
        }
    }

    @Test
    @AllureId("2")
    @DisplayName("Проверка удаления студента")
    fun checkDeletionOfStudent() = run {
        mock(threePeople)
        MainPage {
            clickButtonAddPerson()
//            clickButtonNetwork()
            repeat(3) { clickButtonNetwork() }
            Thread.sleep(2000)
            //TODO свайпнуть кого-то
            //TODO проверить удаление
        }
    }

    @Test
    @AllureId("3")
    @DisplayName("Проверка выбора по умолчанию в окне сортировки")
    fun checkDefaultSelectionInSortWindow() = run {
        MainPage {
            clickSortButton()
            checkIsChecked()
        }
    }

    @Test
    @AllureId("4")
    @DisplayName("Проверка сортировки по возрасту")
    fun checkAgeSorting() = run {
        mock(threePeople)
        MainPage {
            clickButtonAddPerson()
            repeat(3) { clickButtonNetwork() }
            clickSortButton()
            clickAge()
//            Thread.sleep(20000)
            //TODO сделать проверку сортировки по возрастанию
        }
    }

    companion object {
        val threePeople = listOf(
            PersonDescription(),
            PersonDescription(
                name = "Anton",
                lastname = "MMMs",
                age = 44
            ),
            PersonDescription(
                name = "Kiril",
                lastname = "MDS",
                age = 22
            )
        )
    }
}