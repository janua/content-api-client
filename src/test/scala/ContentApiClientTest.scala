package test

import contentapiclient.{ContentApiQuery, ContentApiClient}
import org.scalatest.{ShouldMatchers, FlatSpec}
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.time.{Millis, Seconds, Span}

class ContentApiClientTest extends FlatSpec with ShouldMatchers with Futures with ScalaFutures {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(5, Millis))

  "ContentApiClient" should "return a 200" in {
    val contentResult = ContentApiClient.get("artanddesign/2013/nov/28/janet-delaney-best-photograph")
    whenReady(contentResult) { response =>
      response.getStatusCode should be (200)
    }
  }

  it should "get Content" in {
    val contentResult = ContentApiClient.getContent("artanddesign/2013/nov/28/janet-delaney-best-photograph")
    whenReady(contentResult) { content =>
      content.isDefined should be (true)
      content.get.status should be ("ok")
      content.get.content.get.id should be ("artanddesign/2013/nov/28/janet-delaney-best-photograph")
    }
  }

  it should "get Results" in {
    val queryParams = ContentApiQuery("uk")
      .withEdition("uk")
      .withShowEditorsPicks(true)
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.editorsPicks.get.length should be > 15
    }
  }

}
