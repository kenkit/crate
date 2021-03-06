/*
 * Licensed to Crate under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Crate licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial
 * agreement.
 */

package io.crate.execution.ddl;

import io.crate.metadata.RelationName;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.support.master.AcknowledgedRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;
import java.util.List;

public final class SwapRelationsRequest extends AcknowledgedRequest<SwapRelationsRequest> {

    private List<RelationNameSwap> swapRelations;
    private List<RelationName> dropRelations;

    SwapRelationsRequest() {
    }

    public SwapRelationsRequest(List<RelationNameSwap> swapRelations, List<RelationName> dropRelations) {
        this.swapRelations = swapRelations;
        this.dropRelations = dropRelations;
    }

    /**
     * Relations to swap.
     * All actions will be relative to the cluster state at the start of the swap operation and independent of each other.
     */
    public List<RelationNameSwap> swapActions() {
        return swapRelations;
    }

    /**
     * Relations to drop. This will be relative to the cluster state that has the re-name operations already applied.
     */
    public List<RelationName> dropRelations() {
        return dropRelations;
    }

    @Override
    public ActionRequestValidationException validate() {
        return null;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        swapRelations = in.readList(RelationNameSwap::new);
        dropRelations = in.readList(RelationName::new);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeList(swapRelations);
        out.writeList(dropRelations);
    }
}
