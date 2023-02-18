import fission.FissionFunction
import fission.Function
import fission.Header
import fission.Request

fun main() {
    FissionFunction.init(EchoFunction())
}

class EchoFunction : Function {
    override fun onCall(request: Request, headers: List<Header>): String {
        return request.content
    }

}