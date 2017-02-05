package io.herdes.shared.orient

import akka.actor.{Actor, ActorLogging}

private[orient] abstract class AbstractRepositoryActor[T <: Entity[String]](repository: Repository[T]) extends Actor with ActorLogging {
  private implicit val system = context.system
  private implicit val dispatcher = system.dispatcher

  override def receive: Receive = {
    case GetItem(id) =>
      sender ! repository.findById(id)

    case ListItems() =>
      sender ! repository.findAll()

    case SaveItem(item: T) =>
      sender ! repository.save(item)

    case DeleteItem(id) =>
      val item = repository.findById(id)
      sender ! repository.delete(item)
  }
}

