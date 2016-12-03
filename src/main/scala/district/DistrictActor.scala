package district


import akka.actor.{Actor, ActorRef}
import district.DistrictActor._
import items.Item

import scala.collection.mutable.ListBuffer

object DistrictActor {

  case object Players

  case class AddPlayer(player: ActorRef)
  case class RemovePlayer(player: ActorRef)

  case object Items

  case class AddItem(item: Item)
  case class RemoveItem(item: Item)

}

class DistrictActor extends Actor {
  var players: ListBuffer[ActorRef] = ListBuffer[ActorRef]()
  var items: ListBuffer[Item] = ListBuffer[Item]()

  override def receive: Receive = {
    case Players => sender() ! players
    case AddPlayer(playerRef) => players += playerRef
    case RemovePlayer(playerRef) => players -= playerRef
    case Items => sender ! items
    case AddItem(item) => items = items += item
    case RemoveItem(item) => items = items -= item
  }
}
