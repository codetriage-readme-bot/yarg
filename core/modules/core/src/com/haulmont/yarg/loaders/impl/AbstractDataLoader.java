/*
 * Copyright 2013 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 *
 * @author degtyarjov
 * @version $Id$
 */
package com.haulmont.yarg.loaders.impl;

import com.haulmont.yarg.loaders.ReportFieldsConverter;
import com.haulmont.yarg.loaders.ReportDataLoader;
import com.haulmont.yarg.loaders.ReportParametersConverter;

public abstract class AbstractDataLoader implements ReportDataLoader {
    protected ReportParametersConverter parametersConverter = null;
    protected ReportFieldsConverter fieldsConverter = null;


    protected <T> T convertParameter(Object input) {
        if (parametersConverter != null) {
            return parametersConverter.convert(input);
        } else {
            return (T) input;
        }
    }

    protected <T> T convertOutputValue(Object input) {
        if (fieldsConverter != null) {
            return fieldsConverter.convert(input);
        } else {
            return (T) input;
        }
    }

    public void setParametersConverter(ReportParametersConverter reportParametersConverter) {
        this.parametersConverter = reportParametersConverter;
    }

    public void setFieldsConverter(ReportFieldsConverter reportFieldsConverter) {
        this.fieldsConverter = reportFieldsConverter;
    }

    public ReportParametersConverter getParametersConverter() {
        return parametersConverter;
    }

    public ReportFieldsConverter getFieldsConverter() {
        return fieldsConverter;
    }
}
