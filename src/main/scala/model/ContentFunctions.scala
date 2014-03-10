package model

import org.apache.commons.math3.fraction.Fraction

trait ContentFunctions { self: Content =>
  lazy val videos: Seq[Element] = elements.map(_.filter(_.relation == "video")).getOrElse(Nil)
  lazy val images: Seq[Element] = elements.map(_.filter(_.relation == "image")).getOrElse(Nil)

  lazy val imagesWithIndex: Seq[(Element, Int)] = images.zipWithIndex

  def getLargestImage: Option[Element] = images.sortBy(_.largestImageAsset.flatMap(_.asset.width).getOrElse(0)).headOption
}

trait AssetFunctions { self: Asset =>

  private lazy val aspectRatio: Fraction = {
    val heightAsRatio: Int = height.getOrElse(0) match {
      case 0 => 1
      case denom:Int => denom
    }
    new Fraction(width.getOrElse(0), heightAsRatio)
  }

}

case class Video(asset: Asset)

object Video {
  def unapply(asset: Asset): Option[Video] = if (asset.assetType == "video") Option(Video(asset)) else None
}

case class Audio(asset: Asset)

object Audio {
  def unapply(asset: Asset): Option[Audio] = if (asset.assetType == "audio") Option(Audio(asset)) else None
}

case class Image(asset: Asset)

object Image {
  def unapply(asset: Asset): Option[Image] = if (asset.assetType == "image") Option(Image(asset)) else None
}