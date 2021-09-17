//package kotlin.ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.sber.qa.CertificateRequest
import ru.sber.qa.CertificateType
import ru.sber.qa.Scanner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CertificaterRequestTest {

    private lateinit var certificateRequest: CertificateRequest

    @BeforeEach
    fun beforeTest() {
        mockkObject(Scanner)
        every {Scanner.getScanData() } returns ByteArray(1)
    }

    @Test
    fun `проверка получения справки`() {
        certificateRequest = CertificateRequest(15, CertificateType.NDFL)
        val certificate = certificateRequest.process(15)

        assertNotNull(certificate)
        // проверка получения НДФЛ
        assertEquals(CertificateType.NDFL, getCertificateTypeFromProcess(CertificateType.NDFL), "Тип справки отличается от ожидаемого")
        // проверка получения Трудовой книжки
        assertEquals(CertificateType.LABOUR_BOOK, getCertificateTypeFromProcess(CertificateType.LABOUR_BOOK), "Тип справки отличается от ожидаемого")
    }

    fun getCertificateTypeFromProcess(type : CertificateType): CertificateType {
        certificateRequest = CertificateRequest(15, type)

        return  certificateRequest.process(15).certificateRequest.certificateType
    }

    @Test
    fun `проверка вызова метода сканирования`() {

        every { Scanner.getScanData() } returns ByteArray(1)
        certificateRequest = CertificateRequest(15, CertificateType.NDFL)
        certificateRequest.process(15)

        verify(exactly = 1){ Scanner.getScanData()}
    }

}