import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import io.ktor.gson.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
    }
}

fun HTML.kafka() {
    head {
        title("Hello from Kafka page!")
    }
    body {
        div {
            +"Hello from Kafka page"
        }
    }
}

data class Town(val name: String)

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson{
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }
    install(Routing) {
        get("/") {
            call.respondHtml(io.ktor.http.HttpStatusCode.OK, kotlinx.html.HTML::index)
        }
        get("/data-class") {

            call.respond(
                Town("Tokyo")
            )
        }
    }
}

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("BlogAppKt"), module = Application::module).start()
}