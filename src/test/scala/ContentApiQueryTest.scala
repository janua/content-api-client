package test

import org.scalatest.{ShouldMatchers, FlatSpec}
import contentapiclient.ContentApiQuery
import org.scalatest.concurrent.ScalaFutures

class ContentApiQueryTest extends FlatSpec with ShouldMatchers with ScalaFutures {

  "ContentApiQuery" should "query for an id properly through id" in {
    whenReady(ContentApiQuery("uk/culture").get) { response =>
      response.get.status should be ("ok")
    }
  }

}
