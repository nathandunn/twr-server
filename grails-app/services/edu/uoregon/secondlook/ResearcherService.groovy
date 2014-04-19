package edu.uoregon.secondlook

import grails.transaction.Transactional
import org.apache.shiro.SecurityUtils

@Transactional
class ResearcherService {

    static String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR"
    static String ROLE_USER = "ROLE_USER"

    ResearcherUser getCurrentUser(){
        String currentUserName = SecurityUtils?.subject?.principal
        if(currentUserName){
//        treeList.eachWithIndex{ LeafyTree<Photo> tree, int i ->
//            println "RE-ORDER TREE[${i}]->size(${tree.findRoot().countLeaves()}): ${tree.findRoot().display()}"
//        }
            return Researcher.findByUsername(currentUserName)
        }
        return null
    }

    boolean isAdmin() {
        String currentUserName = SecurityUtils?.subject?.principal
        if(currentUserName){
            ResearcherUser researcher = ResearcherUser.findByUsername(currentUserName)
            for(ResearcherRole role in researcher.roles){
                if(role.name==ROLE_ADMINISTRATOR){
                    return true
                }
            }
        }
        return false
    }
}
