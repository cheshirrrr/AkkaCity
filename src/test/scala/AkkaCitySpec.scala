import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import district.DistrictActor
import district.DistrictActor._
import item.Item
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import player.PlayerActor
import player.PlayerActor.Move

class AkkaCitySpec() extends TestKit(ActorSystem("AkkaCitySpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "District actor" must {

    "must add player to list of players and remove him" in {
      val district = TestActorRef(new DistrictActor)
      val districtActor = district.underlyingActor

      districtActor.players shouldBe empty

      val player = system.actorOf(Props(classOf[PlayerActor], district))
      district ! Players
      districtActor.players should contain only player

      district ! RemovePlayer(player)

      districtActor.players shouldBe empty
    }

    "must add item to list of items and remove it" in {
      val district = TestActorRef(new DistrictActor)
      val districtActor = district.underlyingActor
      districtActor.items shouldBe empty

      val item = Item("garbage")
      district ! AddItem(item)
      districtActor.items should contain only item

      district ! RemoveItem(item)
      districtActor.items shouldBe empty
    }
  }

  "PlayerActor" must {

    "must change districts when moving" in {
      val oldDistrict = TestActorRef(new DistrictActor)
      val oldDistrictActor = oldDistrict.underlyingActor

      oldDistrictActor.players shouldBe empty

      val player = TestActorRef(new PlayerActor(oldDistrict))
      val playerActor = player.underlyingActor

      playerActor.location shouldBe oldDistrict
      oldDistrictActor.players should contain only player

      val district = TestActorRef(new DistrictActor)
      val districtActor = district.underlyingActor

      player ! Move(district)

      playerActor.location shouldBe district
      oldDistrictActor.players shouldBe empty
      districtActor.players should contain only player

    }
  }
}
