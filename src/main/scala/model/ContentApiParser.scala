package model

import play.api.libs.json.Json
import com.ning.http.client.Response

trait ContentApiParser {

  implicit val contentApiResponseRead = Json.reads[ContentApiResponse]

  def parseResponse(r: String): Option[ContentApiResponse] =
    Json.parse(r).asOpt[ContentApiResponse]

  def parseResponse(r: Response): Option[ContentApiResponse] =
    parseResponse(r.getResponseBody)
}

object ContentApiParser extends ContentApiParser
