/**
 * eobjects.org DataCleaner
 * Copyright (C) 2010 eobjects.org
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.eobjects.datacleaner.monitor.server;

import java.util.List;

import javax.servlet.ServletException;

import org.eobjects.datacleaner.monitor.scheduling.SchedulingService;
import org.eobjects.datacleaner.monitor.scheduling.model.ScheduleDefinition;
import org.eobjects.datacleaner.monitor.shared.model.TenantIdentifier;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Servlet wrapper/proxy for the {@link SchedulingService}. Passes all service
 * requests on to a delegate, see {@link #setDelegate(SchedulingService)} and
 * {@link #getDelegate()}.
 */
public class SchedulingServiceServlet extends RemoteServiceServlet implements SchedulingService {

    private static final long serialVersionUID = 1L;

    private SchedulingService _delegate;

    @Override
    public void init() throws ServletException {
        super.init();

        if (_delegate == null) {
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            SchedulingService delegate = applicationContext.getBean(SchedulingService.class);
            if (delegate == null) {
                throw new ServletException("No delegate found in application context!");
            }
            _delegate = delegate;
        }
    }

    public SchedulingService getDelegate() {
        return _delegate;
    }

    public void setDelegate(SchedulingService delegate) {
        _delegate = delegate;
    }

    @Override
    public List<ScheduleDefinition> getSchedules(TenantIdentifier tenant) {
        return _delegate.getSchedules(tenant);
    }

    @Override
    public ScheduleDefinition updateSchedule(TenantIdentifier tenant, ScheduleDefinition scheduleDefinition) {
        return _delegate.updateSchedule(tenant, scheduleDefinition);
    }

}
