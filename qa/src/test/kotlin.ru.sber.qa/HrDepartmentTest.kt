//package kotlin.ru.sber.qa

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs

import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.sber.qa.*
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.*


class HrDepartmentTest {

    private val clockStringStartWeek = "2021-09-06T01:01:01.00Z"

    @MockK
    lateinit var clock : Clock
    @MockK
    var certificateRequest = CertificateRequest(15, CertificateType.NDFL)
    @MockK
    private val incomeBox: LinkedList<CertificateRequest> = LinkedList()
    @MockK
    private val outcomeOutcome: LinkedList<Certificate> = LinkedList()

    @InjectMockKs
    val hrDepartment = HrDepartment

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, overrideRecordPrivateCalls = true)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun `проверка невозможности изготовления NDFL в выходные`() {
        certificateRequest = CertificateRequest(15, CertificateType.NDFL)

        hrDepartment.clock = getTestClock(6L)
        Assertions.assertThrows(WeekendDayException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }

        hrDepartment.clock = getTestClock(5L)
        Assertions.assertThrows(WeekendDayException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `проверка невозможности изготовления LABOUR_BOOK в выходные`() {
        certificateRequest = CertificateRequest(15, CertificateType.LABOUR_BOOK)

        hrDepartment.clock = getTestClock(6L)
        Assertions.assertThrows(WeekendDayException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
        hrDepartment.clock = getTestClock(5L)
        Assertions.assertThrows(WeekendDayException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
     fun `проверка невозможности изготовление NDFL справки в вт, чт`() {
        certificateRequest = CertificateRequest(15, CertificateType.NDFL)

        hrDepartment.clock = getTestClock(1L)
        Assertions.assertThrows(NotAllowReceiveRequestException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }

        hrDepartment.clock = getTestClock(3L)
        Assertions.assertThrows(NotAllowReceiveRequestException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
    }
    @Test
    fun `проверка невозможности изготовление LABOUR_BOOK справки в пн, ср, птн`() {
        hrDepartment.clock = getTestClock(0L)
        certificateRequest = CertificateRequest(15, CertificateType.LABOUR_BOOK)
        Assertions.assertThrows(NotAllowReceiveRequestException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
        hrDepartment.clock = getTestClock(2L)
        certificateRequest = CertificateRequest(15, CertificateType.LABOUR_BOOK)
        Assertions.assertThrows(NotAllowReceiveRequestException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
        hrDepartment.clock = getTestClock(4L)
        certificateRequest = CertificateRequest(15, CertificateType.LABOUR_BOOK)
        Assertions.assertThrows(NotAllowReceiveRequestException::class.java) {
            hrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `проверка передачи в очередь справки`() {
//        hrDepartment.clock = getTestClock(0L)
//
//        certificateRequest = CertificateRequest(15, CertificateType.NDFL)
//
//       // every { incomeBox.push(certificateRequest) } returns {incomeBox.add(certificateRequest)}
//
//        hrDepartment.receiveRequest(certificateRequest)
//        assertTrue ("Запрос на справку не сформирован") { 0 < incomeBox.size}
    //todo здесь будет проверка на то, что сообщение было поставлено в очередь, когда пойму как мокировать приватные объекты
    }

    @Test
    fun `проверка вычитки из очереди справки`() {
     //todo здесь будет проверка на то, что сообщение выичтано из очереди, когда пойму как мокировать приватные поля
    }

    fun  getTestClock(delta : Long) : Clock {
        return Clock.fixed(Instant.parse((clockStringStartWeek)).plus(Duration.ofDays(delta)), ZoneId.of("+03:00"))
    }

}