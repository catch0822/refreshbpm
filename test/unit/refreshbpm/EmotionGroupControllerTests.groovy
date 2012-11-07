package refreshbpm



import org.junit.*
import grails.test.mixin.*

@TestFor(EmotionGroupController)
@Mock(EmotionGroup)
class EmotionGroupControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/emotionGroup/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.emotionGroupInstanceList.size() == 0
        assert model.emotionGroupInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.emotionGroupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.emotionGroupInstance != null
        assert view == '/emotionGroup/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/emotionGroup/show/1'
        assert controller.flash.message != null
        assert EmotionGroup.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/emotionGroup/list'


        populateValidParams(params)
        def emotionGroup = new EmotionGroup(params)

        assert emotionGroup.save() != null

        params.id = emotionGroup.id

        def model = controller.show()

        assert model.emotionGroupInstance == emotionGroup
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/emotionGroup/list'


        populateValidParams(params)
        def emotionGroup = new EmotionGroup(params)

        assert emotionGroup.save() != null

        params.id = emotionGroup.id

        def model = controller.edit()

        assert model.emotionGroupInstance == emotionGroup
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/emotionGroup/list'

        response.reset()


        populateValidParams(params)
        def emotionGroup = new EmotionGroup(params)

        assert emotionGroup.save() != null

        // test invalid parameters in update
        params.id = emotionGroup.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/emotionGroup/edit"
        assert model.emotionGroupInstance != null

        emotionGroup.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/emotionGroup/show/$emotionGroup.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        emotionGroup.clearErrors()

        populateValidParams(params)
        params.id = emotionGroup.id
        params.version = -1
        controller.update()

        assert view == "/emotionGroup/edit"
        assert model.emotionGroupInstance != null
        assert model.emotionGroupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/emotionGroup/list'

        response.reset()

        populateValidParams(params)
        def emotionGroup = new EmotionGroup(params)

        assert emotionGroup.save() != null
        assert EmotionGroup.count() == 1

        params.id = emotionGroup.id

        controller.delete()

        assert EmotionGroup.count() == 0
        assert EmotionGroup.get(emotionGroup.id) == null
        assert response.redirectedUrl == '/emotionGroup/list'
    }
}
