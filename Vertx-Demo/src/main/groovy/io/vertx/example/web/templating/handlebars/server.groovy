import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.templ.HandlebarsTemplateEngine

// To simplify the development of the web components we use a Router to route all HTTP requests
// to organize our code in a reusable way.
def router = Router.router(vertx)

// In order to use a template we first need to create an engine
def engine = HandlebarsTemplateEngine.create()

// Entry point to the application, this will render a custom template.
router.get().handler({ ctx ->
  // we define a hardcoded title for our application
  ctx.put("name", "Vert.x Web")

  // and now delegate to the engine to render it.
  engine.render(ctx, "templates/index.hbs", { res ->
    if (res.succeeded()) {
      ctx.response().end(res.result())
    } else {
      ctx.fail(res.cause())
    }
  })
})

// start a HTTP web server on port 8080
vertx.createHttpServer().requestHandler(router.&accept).listen(8080)
