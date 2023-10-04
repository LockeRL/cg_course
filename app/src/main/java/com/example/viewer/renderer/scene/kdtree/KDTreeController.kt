package com.example.viewer.renderer.scene.kdtree

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseFigure
import com.example.viewer.renderer.scene.kdtree.data.FindIntersectionNodeAnswer
import com.example.viewer.renderer.scene.kdtree.data.FindPlaneAnswer
import com.example.viewer.renderer.scene.kdtree.data.Plane
import com.example.viewer.renderer.scene.kdtree.data.Voxel
import com.example.viewer.renderer.toInt
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

object KDTreeController {

    fun buildKDTree(objects: MutableList<BaseFigure>): KDTree {
        println("in buildKDTree")
        val vox = makeInitVoxel(objects)
        val root = recBuild(objects, objects.size, vox, 0)
        return KDTree(root, vox)
    }

    fun findIntersectionTree(
        tree: KDTree,
        vecStart: Vector3D,
        vec: Vector3D
    ): FindIntersectionNodeAnswer {
        val ans = FindIntersectionNodeAnswer(nearestPointDist = Double.MAX_VALUE, status = false)
        val recAnswer = findIntersectionNode(tree.root, tree.vox, vecStart, vec, ans)
        if (NO_BOUNDING_BOX)
            return recAnswer.apply { status = status && voxelIntersection(vec, vecStart, tree.vox) }

        return recAnswer
    }

    private fun pointInVoxel(point: Vector3D, vox: Voxel) = (
            (point.x > vox.xMin) && (point.x < vox.xMax) &&
                    (point.y > vox.yMin) && (point.y < vox.yMax) &&
                    (point.z > vox.zMin) && (point.z < vox.zMax)
            )

    private fun makeInitVoxel(objects: List<BaseFigure>): Voxel {
        println("init voxel")
        if (objects.isEmpty())
            return Voxel(-1.0, -1.0, -1.0, 1.0, 1.0, 1.0)

        var (xMin, yMin, zMin) = objects[0].getMinBoundaryPoint()
        var (xMax, yMax, zMax) = objects[0].getMaxBoundaryPoint()

        objects.forEach { obj ->
            val minP = obj.getMinBoundaryPoint()
            val maxP = obj.getMaxBoundaryPoint()

            xMin = min(xMin, minP.x)
            yMin = min(yMin, minP.y)
            zMin = min(zMin, minP.z)

            xMax = max(xMax, maxP.x)
            yMax = max(yMax, maxP.y)
            zMax = max(zMax, maxP.z)
        }

        return Voxel(xMin - 1.0, yMin - 1.0, zMin - 1.0, xMax + 1.0, yMax + 1.0, zMax + 1.0)
    }

    private fun objectInVoxel(obj: BaseFigure, vox: Voxel): Boolean {
        val minP = obj.getMinBoundaryPoint()
        val maxP = obj.getMaxBoundaryPoint()

        return !((maxP.x < vox.xMin) || (maxP.y < vox.yMin) || (maxP.z < vox.zMin) || (minP.x > vox.xMax) || (minP.y > vox.yMax) || (minP.z > vox.zMax))
    }

    private fun objectsInVoxel(objects: List<BaseFigure>, objectsCount: Int, vox: Voxel): Int {
        var count = 0
        for (i in 0 until objectsCount)
            count += objectInVoxel(objects[i], vox).toInt()
        return count
    }

    private fun makeLeaf(objects: List<BaseFigure>, objectsCount: Int) =
        KDNode().apply { this.objects = objects.take(objectsCount).toMutableList() }.also { println("in make leaf") }

    private fun vectorPlaneIntersection(
        vec: Vector3D,
        vecStart: Vector3D,
        plane: Plane,
        coord: Double
    ) =
        when (plane) {
            Plane.XY -> if (((coord < vecStart.z) && (vec.z > 0.0)) || ((coord > vecStart.z) && (vec.z < 0.0))) null else let {
                val k = (coord - vecStart.z) / vec.z
                Vector3D(vecStart.x + vec.x * k, vecStart.y + vec.y * k, coord)
            }

            Plane.XZ -> if (((coord < vecStart.y) && (vec.y > 0.0)) || ((coord > vecStart.y) && (vec.y < 0.0))) null else let {
                val k = (coord - vecStart.y) / vec.y
                Vector3D(vecStart.x + vec.x * k, coord, vecStart.z + vec.z * k)
            }

            Plane.YZ -> if (((coord < vecStart.x) && (vec.x > 0.0)) || ((coord > vecStart.x) && (vec.x < 0.0))) null else let {
                val k = (coord - vecStart.x) / vec.x
                Vector3D(coord, vecStart.y + vec.y * k, vecStart.z + vec.z * k)
            }

            else -> let {
                println("Plane is None")
                null
            }
        }

    private fun voxelIntersection(vec: Vector3D, vecStart: Vector3D, vox: Voxel): Boolean {
        if (pointInVoxel(vecStart, vox))
            return true

        var c = vox.zMin
        var p = vectorPlaneIntersection(vec, vecStart, Plane.XY, c)
        if ((p != null) && (p.x > vox.xMin) && (p.x < vox.xMax) && (p.y > vox.yMin) && (p.y < vox.yMax))
            return true

        c = vox.zMax
        p = vectorPlaneIntersection(vec, vecStart, Plane.XY, c)
        if ((p != null) && (p.x > vox.xMin) && (p.x < vox.xMax) && (p.y > vox.yMin) && (p.y < vox.yMax))
            return true

        c = vox.yMin
        p = vectorPlaneIntersection(vec, vecStart, Plane.XZ, c)
        if ((p != null) && (p.x > vox.xMin) && (p.x < vox.xMax) && (p.z > vox.zMin) && (p.z < vox.zMax))
            return true

        c = vox.yMax
        p = vectorPlaneIntersection(vec, vecStart, Plane.XZ, c)
        if ((p != null) && (p.x > vox.xMin) && (p.x < vox.xMax) && (p.z > vox.zMin) && (p.z < vox.zMax))
            return true

        c = vox.xMin
        p = vectorPlaneIntersection(vec, vecStart, Plane.YZ, c)
        if ((p != null) && (p.z > vox.zMin) && (p.z < vox.zMax) && (p.y > vox.yMin) && (p.y < vox.yMax))
            return true

        c = vox.xMax
        p = vectorPlaneIntersection(vec, vecStart, Plane.YZ, c)
        if ((p != null) && (p.z > vox.zMin) && (p.z < vox.zMax) && (p.y > vox.yMin) && (p.y < vox.yMax))
            return true

        return false
    }

    private fun splitVoxel(vox: Voxel, plane: Plane, coord: Double, vl: Voxel, vr: Voxel) {
        vl.set(vox)
        vr.set(vox)
        when (plane) {
            Plane.XY -> {
                vl.zMax = coord
                vr.zMin = coord
            }

            Plane.XZ -> {
                vl.yMax = coord
                vr.yMin = coord
            }

            Plane.YZ -> {
                vl.xMax = coord
                vr.xMin = coord
            }

            else -> println("Plane is None")
        }
    }

    /*
 * Using Surface Area Heuristic (SAH) for finding best split pane
 *
 * SAH = 0.5 * voxel_surface_area * number_of_objects_in_voxel
 *
 * splitted_SAH = split_cost
 *                + 0.5 * left_voxel_surface_area * number_of_objects_in_left_voxel
 *                + 0.5 * right_voxel_surface_area * number_of_objects_in_right_voxel
 *
 * Finding coordinate of split plane (XY, XZ or YZ) which minimizing SAH
 *
 * If can't find optimal split plane - returns NONE
 *
 * see: http://stackoverflow.com/a/4633332/653511
 */

    private fun findPlane(
        objects: List<BaseFigure>,
        objectsCount: Int,
        vox: Voxel,
        depth: Int
    ): FindPlaneAnswer {
        val answer = FindPlaneAnswer()
        if ((depth >= MAX_TREE_DEPTH) || (objects.size <= OBJECTS_IN_LEAF))
            return answer

        val hx = vox.xMax - vox.xMin
        val hy = vox.yMax - vox.yMin
        val hz = vox.zMax - vox.zMin

        var sxy = hx * hy
        var sxz = hx * hz
        var syz = hy * hz
        val sum = sxy + sxz + syz

        // normalize sxy + sxz + syz = 1
        sxy /= sum
        sxz /= sum
        syz /= sum

        var bestSAH = objectsCount.toDouble()

        var currSAH: Double
        var curSplitCoord: Double
        val vl = Voxel()
        val vr = Voxel()
        var l: Double
        var r: Double

        var sSplit: Double
        var sNonSplit: Double

        // Minimize SAH splitting across XY plane
        sSplit = sxy
        sNonSplit = sxz + syz
        for (i in 1 until MAX_SPLITS_OF_VOXEL) {
            l = i.toDouble() / MAX_SPLITS_OF_VOXEL
            r = 1.0 - l

            curSplitCoord = vox.zMin + l * hz

            splitVoxel(vox, Plane.XY, curSplitCoord, vl, vr)

            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, objectsCount, vl) +
                    (sSplit + r * sNonSplit) * objectsInVoxel(objects, objectsCount, vr) +
                    SPLIT_COST

            if (currSAH < bestSAH) {
                bestSAH = currSAH
                answer.plane = Plane.XY
                answer.coord = curSplitCoord
            }
        }

        // Minimize SAH splitting across XZ plane
        sSplit = sxz
        sNonSplit = sxy + syz
        for (i in 1 until MAX_SPLITS_OF_VOXEL) {
            l = i.toDouble() / MAX_SPLITS_OF_VOXEL
            r = 1.0 - l

            curSplitCoord = vox.yMin + l * hy

            splitVoxel(vox, Plane.XZ, curSplitCoord, vl, vr)

            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, objectsCount, vl) +
                    (sSplit + r * sNonSplit) * objectsInVoxel(objects, objectsCount, vr) +
                    SPLIT_COST

            if (currSAH < bestSAH) {
                bestSAH = currSAH
                answer.plane = Plane.XZ
                answer.coord = curSplitCoord
            }
        }

        // Minimize SAH splitting across YZ plane
        sSplit = syz
        sNonSplit = sxy + sxz
        for (i in 1 until MAX_SPLITS_OF_VOXEL) {
            l = i.toDouble() / MAX_SPLITS_OF_VOXEL
            r = 1.0 - l

            curSplitCoord = vox.xMin + l * hx

            splitVoxel(vox, Plane.YZ, curSplitCoord, vl, vr)

            currSAH = (sSplit + l * sNonSplit) * objectsInVoxel(objects, objectsCount, vl) +
                    (sSplit + r * sNonSplit) * objectsInVoxel(objects, objectsCount, vr) +
                    SPLIT_COST

            if (currSAH < bestSAH) {
                bestSAH = currSAH
                answer.plane = Plane.YZ
                answer.coord = curSplitCoord
            }
        }

        return answer
    }

    private fun findIntersectionNode(
        node: KDNode,
        vox: Voxel,
        vecStart: Vector3D,
        vec: Vector3D,
        answer: FindIntersectionNodeAnswer
    ): FindIntersectionNodeAnswer {
        if (node.plane == Plane.NONE) {
            if (node.objects.isNotEmpty()) {
                var intersected = false
                var sqrNearestDist = 0.0
                var nearestObj: BaseFigure? = null
                var nearestIntersectionPoint: Vector3D? = null

                node.objects.forEach { obj ->
                    val intersectionPoint = obj.intersect(vecStart, vec) ?: return@forEach
                    if (pointInVoxel(intersectionPoint, vox)) {
                        val sqrCurrDist =
                            Vector3D.vecFromPoints(vecStart, intersectionPoint).moduleSquare()
                        if (!intersected || (sqrCurrDist < sqrNearestDist)) {
                            nearestObj = obj
                            nearestIntersectionPoint = intersectionPoint
                            sqrNearestDist = sqrCurrDist
                            intersected = true
                        }
                    }
                }

                if (intersected) {
                    val nearestDist = sqrt(sqrNearestDist)
                    if (nearestDist < answer.nearestPointDist) {
                        answer.nearestObject = nearestObj
                        answer.nearestPointDist = nearestDist
                        answer.nearestPoint = nearestIntersectionPoint
                    }
                }
                answer.status = intersected
                return answer

            }
            answer.status = false
            return answer
        }

        val frontNode: KDNode?
        val backNode: KDNode?
        val frontVoxel = Voxel()
        val backVoxel = Voxel()

        when (node.plane) {
            Plane.XY -> if (((node.coord > vox.zMin) && (node.coord > vecStart.z)) || ((node.coord < vox.zMin) && (node.coord < vecStart.z))) {
                frontNode = node.left
                backNode = node.right
                splitVoxel(vox, node.plane, node.coord, frontVoxel, backVoxel)
            } else {
                frontNode = node.right
                backNode = node.left
                splitVoxel(vox, node.plane, node.coord, backVoxel, frontVoxel)
            }

            Plane.XZ -> if (((node.coord > vox.yMin) && (node.coord > vecStart.y)) || ((node.coord < vox.yMin) && (node.coord < vecStart.y))) {
                frontNode = node.left
                backNode = node.right
                splitVoxel(vox, node.plane, node.coord, frontVoxel, backVoxel)
            } else {
                frontNode = node.right
                backNode = node.left
                splitVoxel(vox, node.plane, node.coord, backVoxel, frontVoxel)
            }

            Plane.YZ -> if (((node.coord > vox.xMin) && (node.coord > vecStart.x)) || ((node.coord < vox.xMin) && (node.coord < vecStart.x))) {
                frontNode = node.left
                backNode = node.right
                splitVoxel(vox, node.plane, node.coord, frontVoxel, backVoxel)
            } else {
                frontNode = node.right
                backNode = node.left
                splitVoxel(vox, node.plane, node.coord, backVoxel, frontVoxel)
            }

            else -> {
                println("Plane is None")
                frontNode = null
                backNode = null
            }
        }

        val prevAns = findIntersectionNode(frontNode!!, frontVoxel, vecStart, vec, answer)
        if (voxelIntersection(vec, vecStart, frontVoxel) && prevAns.status)
            return prevAns


        return findIntersectionNode(
            backNode!!,
            backVoxel,
            vecStart,
            vec,
            answer
        ).apply { status = status && voxelIntersection(vec, vecStart, backVoxel) }
    }

    private fun filterOverlappedObjects(objects: MutableList<BaseFigure>, objectsCount: Int, vox: Voxel): Int {
        var i = 0
        var j = objectsCount - 1

        while (i <= j) {
            while ((i < j) && objectInVoxel(objects[i], vox))
                i++
            while ((j > i) && (!objectInVoxel(objects[j], vox)))
                j--
            objects[i] = objects[j].also { objects[j] = objects[i] }
            i++
            j--
        }

        return i
    }

    private fun recBuild(objects: MutableList<BaseFigure>, objectsCount: Int, vox: Voxel, iter: Int): KDNode {
        println("in rec build")
        val ans = findPlane(objects, objectsCount, vox, iter)
        if (ans.plane == Plane.NONE)
            return makeLeaf(objects, objectsCount)

        val vl = Voxel()
        val vr = Voxel()
        splitVoxel(vox, ans.plane, ans.coord, vl, vr)

        val lObjectsCount = filterOverlappedObjects(objects, objectsCount, vl)
        val l = recBuild(objects, lObjectsCount, vl, iter + 1)

        val rObjectsCount = filterOverlappedObjects(objects, objectsCount, vr)
        val r = recBuild(objects, rObjectsCount, vr, iter + 1)

        val node = KDNode()
        node.objects = mutableListOf()
        node.plane = ans.plane
        node.coord = ans.coord
        node.left = l
        node.right = r
        return node
    }

    private const val MAX_TREE_DEPTH = 20
    private const val OBJECTS_IN_LEAF = 1
    private const val MAX_SPLITS_OF_VOXEL = 5
    private const val SPLIT_COST = 5
    private const val NO_BOUNDING_BOX = false

}