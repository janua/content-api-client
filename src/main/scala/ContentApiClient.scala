package contentapiclient

import scala.concurrent.{ExecutionContext, Future}
import dispatch.{url, Http}
import com.ning.http.client.Response
import model.ContentApiParser


trait ContentApiClient {

  import ExecutionContext.Implicits.global

  private val apiUrl: String = ""

  private def getUrl(id: String): String = s"$apiUrl/$id"

  def get(id: String) = Http(url(getUrl(id)))

  def get(query: ContentApiQuery): Future[Response] =
    Http(query.params.foldLeft(url(getUrl(query.id))){case (req, (k, v)) => req.addQueryParameter(k, v)})

  def getContent(id: String) =
    get(id).map(response => ContentApiParser.parseResponse(response.getResponseBody))

  def getContent(query: ContentApiQuery) =
    get(query).map(response => ContentApiParser.parseResponse(response.getResponseBody))

}

object ContentApiClient extends ContentApiClient