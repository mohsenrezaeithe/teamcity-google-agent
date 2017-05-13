/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jetbrains.buildServer.clouds.google.web

import jetbrains.buildServer.clouds.google.connector.GoogleApiConnector
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jdom.Element
import javax.servlet.http.HttpServletRequest

/**
 * Handles machine types request.
 */
internal class MachineTypesHandler : GoogleResourceHandler() {
    override fun handle(connector: GoogleApiConnector, request: HttpServletRequest) = async(CommonPool) {
        val machineTypes = connector.getMachineTypesAsync().await()
        val machineTypesElement = Element("machineTypes")

        for ((id, displayName) in machineTypes) {
            machineTypesElement.addContent(Element("machineType").apply {
                setAttribute("id", id)
                text = displayName
            })
        }

        machineTypesElement
    }
}
