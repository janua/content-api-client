package test

import contentapiclient.{ContentApiQuery, ContentApiClient}
import org.scalatest.{ShouldMatchers, FlatSpec}
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.time.{Millis, Seconds, Span}

class ContentApiClientTest extends FlatSpec with ShouldMatchers with Futures with ScalaFutures {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(5, Millis))

  "ContentApiClient" should "return a 200" in {
    val contentResult = ContentApiClient.getResponse("artanddesign/2013/nov/28/janet-delaney-best-photograph")
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

  it should "get editorsPicks" in {
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

  it should "get results" in {
    val queryParams = ContentApiQuery("politics")
      .withEdition("uk")
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.results.get.length should be (10)
    }
  }

  it should "get the correct page size" in {
    val queryParams = ContentApiQuery("politics")
      .withEdition("uk")
      .withPageSize("4")
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.results.get.length should be (4)
    }
  }

  it should "get the correct tags" in {
    val queryParams = ContentApiQuery("politics")
      .withEdition("uk")
      .withShowTags("all")
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.results.get(1).tags.get.length should be > 1
    }
  }

  it should "get section" in {
    val queryParams = ContentApiQuery("uk-news")
      .withEdition("uk")
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.section.get.id should be ("uk-news")
      response.get.section.get.editions.length should be > 0
    }
  }

  it should "get an error response" in {
    val queryParams = ContentApiQuery("non-existant-id")
      .withEdition("uk")
    val contentResult = ContentApiClient.getContent(queryParams)
    whenReady(contentResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("error")
      response.get.section.isDefined should be (false)
      response.get.results.isDefined should be (false)
    }
  }

  it should "get a search response" in {
    val searchResult = ContentApiClient.search("london")
    whenReady(searchResult) { response =>
      response.isDefined should be (true)
      response.get.status should be ("ok")
      response.get.results.isDefined should be (true)
      response.get.section.isDefined should be (false)
      response.get.results.get.length should be > 5
    }
  }

}
