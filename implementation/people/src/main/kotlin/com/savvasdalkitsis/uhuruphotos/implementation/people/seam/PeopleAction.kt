/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.implementation.people.seam

import com.savvasdalkitsis.uhuruphotos.api.people.view.state.Person

sealed class PeopleAction {
    data class PersonSelected(val person: Person) : PeopleAction()
    object LoadPeople : PeopleAction()
    object ToggleSortOrder : PeopleAction()
    object NavigateBack : PeopleAction()
    object SwipeToRefresh : PeopleAction()
}
