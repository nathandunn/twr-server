package edu.uoregon.secondlook

import org.springframework.dao.DataIntegrityViolationException

class PatchController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [patchInstanceList: Patch.list(params), patchInstanceTotal: Patch.count()]
    }

    def create() {
        [patchInstance: new Patch(params)]
    }

    def save() {
        def patchInstance = new Patch(params)
        if (!patchInstance.save(flush: true)) {
            render(view: "create", model: [patchInstance: patchInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'patch.label', default: 'Patch'), patchInstance.id])
        redirect(action: "show", id: patchInstance.id)
    }

    def show(Long id) {
        def patchInstance = Patch.get(id)
        if (!patchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "list")
            return
        }

        [patchInstance: patchInstance]
    }

    def edit(Long id) {
        def patchInstance = Patch.get(id)
        if (!patchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "list")
            return
        }

        [patchInstance: patchInstance]
    }

    def update(Long id, Long version) {
        def patchInstance = Patch.get(id)
        if (!patchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (patchInstance.version > version) {
                patchInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'patch.label', default: 'Patch')] as Object[],
                          "Another user has updated this Patch while you were editing")
                render(view: "edit", model: [patchInstance: patchInstance])
                return
            }
        }

        patchInstance.properties = params

        if (!patchInstance.save(flush: true)) {
            render(view: "edit", model: [patchInstance: patchInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'patch.label', default: 'Patch'), patchInstance.id])
        redirect(action: "show", id: patchInstance.id)
    }

    def delete(Long id) {
        def patchInstance = Patch.get(id)
        if (!patchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "list")
            return
        }

        try {
            patchInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'patch.label', default: 'Patch'), id])
            redirect(action: "show", id: id)
        }
    }
}
