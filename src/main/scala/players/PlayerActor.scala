package players

import akka.actor.{Actor, ActorRef}
import district.DistrictActor.{AddPlayer, RemovePlayer}
import players.PlayerActor.{Location, Move}

object PlayerActor {

  case class Move(district: ActorRef)

  case object Location

}

class PlayerActor(var location: ActorRef) extends Actor {
  location ! AddPlayer(self)

  override def receive: Receive = {
    case Move(newLocation) =>
      location ! RemovePlayer(self)
      newLocation ! AddPlayer(self)
      location = newLocation
    case Location => sender() ! location
  }
}
