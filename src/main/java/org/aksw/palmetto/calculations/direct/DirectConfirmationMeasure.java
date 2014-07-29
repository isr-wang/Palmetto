/**
 * Copyright (C) 2014 Michael Röder (michael.roeder@unister.de)
 *
 * Licensed under the Creative Commons Attribution-NonCommercial 4.0
 * International Public License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://creativecommons.org/licenses/by-nc/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, a file
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aksw.palmetto.calculations.direct;

import org.aksw.palmetto.calculations.ConfirmationMeasure;
import org.aksw.palmetto.data.SubsetProbabilities;

/**
 * This interface is implemented by all confirmation measures which are using
 * the probabilities directly.
 * 
 * @author Michael Röder
 * 
 */
public interface DirectConfirmationMeasure extends ConfirmationMeasure {

    /**
     * Calculates the confirmation values for the given subset probabilities.
     * 
     * @param subsetProbabilities
     * @return
     */
    public abstract double[] calculateConfirmationValues(SubsetProbabilities subsetProbabilities);
}