package edu.uoregon.secondlook



import org.junit.*
import grails.test.mixin.*

@TestFor(PassageController)
@Mock(Passage)
class PassageControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/passage/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.passageInstanceList.size() == 0
        assert model.passageInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.passageInstance != null
    }

    void testSave() {
        controller.save()

        assert model.passageInstance != null
        assert view == '/passage/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/passage/show/1'
        assert controller.flash.message != null
        assert Passage.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/passage/list'

        populateValidParams(params)
        def passage = new Passage(params)

        assert passage.save() != null

        params.id = passage.id

        def model = controller.show()

        assert model.passageInstance == passage
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/passage/list'

        populateValidParams(params)
        def passage = new Passage(params)

        assert passage.save() != null

        params.id = passage.id

        def model = controller.edit()

        assert model.passageInstance == passage
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/passage/list'

        response.reset()

        populateValidParams(params)
        def passage = new Passage(params)

        assert passage.save() != null

        // test invalid parameters in update
        params.id = passage.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/passage/edit"
        assert model.passageInstance != null

        passage.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/passage/show/$passage.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        passage.clearErrors()

        populateValidParams(params)
        params.id = passage.id
        params.version = -1
        controller.update()

        assert view == "/passage/edit"
        assert model.passageInstance != null
        assert model.passageInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/passage/list'

        response.reset()

        populateValidParams(params)
        def passage = new Passage(params)

        assert passage.save() != null
        assert Passage.count() == 1

        params.id = passage.id

        controller.delete()

        assert Passage.count() == 0
        assert Passage.get(passage.id) == null
        assert response.redirectedUrl == '/passage/list'
    }
}
