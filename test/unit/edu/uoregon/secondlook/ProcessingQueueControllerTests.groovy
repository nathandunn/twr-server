package edu.uoregon.secondlook



import org.junit.*
import grails.test.mixin.*

@TestFor(ProcessingQueueController)
@Mock(ProcessingQueue)
class ProcessingQueueControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/processingQueue/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.processingQueueInstanceList.size() == 0
        assert model.processingQueueInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.processingQueueInstance != null
    }

    void testSave() {
        controller.save()

        assert model.processingQueueInstance != null
        assert view == '/processingQueue/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/processingQueue/show/1'
        assert controller.flash.message != null
        assert ProcessingQueue.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/processingQueue/list'

        populateValidParams(params)
        def processingQueue = new ProcessingQueue(params)

        assert processingQueue.save() != null

        params.id = processingQueue.id

        def model = controller.show()

        assert model.processingQueueInstance == processingQueue
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/processingQueue/list'

        populateValidParams(params)
        def processingQueue = new ProcessingQueue(params)

        assert processingQueue.save() != null

        params.id = processingQueue.id

        def model = controller.edit()

        assert model.processingQueueInstance == processingQueue
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/processingQueue/list'

        response.reset()

        populateValidParams(params)
        def processingQueue = new ProcessingQueue(params)

        assert processingQueue.save() != null

        // test invalid parameters in update
        params.id = processingQueue.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/processingQueue/edit"
        assert model.processingQueueInstance != null

        processingQueue.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/processingQueue/show/$processingQueue.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        processingQueue.clearErrors()

        populateValidParams(params)
        params.id = processingQueue.id
        params.version = -1
        controller.update()

        assert view == "/processingQueue/edit"
        assert model.processingQueueInstance != null
        assert model.processingQueueInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/processingQueue/list'

        response.reset()

        populateValidParams(params)
        def processingQueue = new ProcessingQueue(params)

        assert processingQueue.save() != null
        assert ProcessingQueue.count() == 1

        params.id = processingQueue.id

        controller.delete()

        assert ProcessingQueue.count() == 0
        assert ProcessingQueue.get(processingQueue.id) == null
        assert response.redirectedUrl == '/processingQueue/list'
    }
}
