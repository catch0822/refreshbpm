package refreshbpm



import org.junit.*
import grails.test.mixin.*

@TestFor(EmotionController)
@Mock(Emotion)
class EmotionControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/emotion/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.emotionInstanceList.size() == 0
        assert model.emotionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.emotionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.emotionInstance != null
        assert view == '/emotion/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/emotion/show/1'
        assert controller.flash.message != null
        assert Emotion.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/emotion/list'


        populateValidParams(params)
        def emotion = new Emotion(params)

        assert emotion.save() != null

        params.id = emotion.id

        def model = controller.show()

        assert model.emotionInstance == emotion
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/emotion/list'


        populateValidParams(params)
        def emotion = new Emotion(params)

        assert emotion.save() != null

        params.id = emotion.id

        def model = controller.edit()

        assert model.emotionInstance == emotion
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/emotion/list'

        response.reset()


        populateValidParams(params)
        def emotion = new Emotion(params)

        assert emotion.save() != null

        // test invalid parameters in update
        params.id = emotion.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/emotion/edit"
        assert model.emotionInstance != null

        emotion.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/emotion/show/$emotion.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        emotion.clearErrors()

        populateValidParams(params)
        params.id = emotion.id
        params.version = -1
        controller.update()

        assert view == "/emotion/edit"
        assert model.emotionInstance != null
        assert model.emotionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/emotion/list'

        response.reset()

        populateValidParams(params)
        def emotion = new Emotion(params)

        assert emotion.save() != null
        assert Emotion.count() == 1

        params.id = emotion.id

        controller.delete()

        assert Emotion.count() == 0
        assert Emotion.get(emotion.id) == null
        assert response.redirectedUrl == '/emotion/list'
    }
}
