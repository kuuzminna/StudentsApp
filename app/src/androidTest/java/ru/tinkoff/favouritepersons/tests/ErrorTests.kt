package ru.tinkoff.favouritepersons.tests

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.Fault
import io.qameta.allure.kotlin.AllureId
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import ru.tinkoff.favouritepersons.pages.MainPage

class ErrorTests : BaseTest() {

    @Test
    @AllureId("10")
    @DisplayName("Проверка отображения сообщения об ошибке интернет-соединения")
    fun checkDisplayErrorMassageAboutInternetConnection() = run {
        stubFor(
            get(urlEqualTo("/api/"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withFault(Fault.EMPTY_RESPONSE)
                )
        )
        MainPage {
            clickButtonAddPerson()
            clickButtonNetwork()
            flakySafely (15_000, 500) {
                checkSnackbarDisplayed()
            }
        }
    }
}