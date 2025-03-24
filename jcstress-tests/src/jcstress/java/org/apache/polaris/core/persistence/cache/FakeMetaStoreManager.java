/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.polaris.core.persistence.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.apache.polaris.core.PolarisCallContext;
import org.apache.polaris.core.entity.*;
import org.apache.polaris.core.persistence.PolarisMetaStoreManager;
import org.apache.polaris.core.persistence.dao.entity.*;
import org.apache.polaris.core.storage.PolarisStorageActions;

public class FakeMetaStoreManager implements PolarisMetaStoreManager {
  private final Supplier<ResolvedEntityResult> results;

  public FakeMetaStoreManager(Supplier<ResolvedEntityResult> results) {
    this.results = results;
  }

  private synchronized ResolvedEntityResult nextResult() {
    return results.get();
  }

  @Override
  public BaseResult bootstrapPolarisService(PolarisCallContext callCtx) {
    return null;
  }

  @Override
  public BaseResult purge(PolarisCallContext callCtx) {
    return null;
  }

  @Override
  public EntityResult readEntityByName(
      PolarisCallContext callCtx,
      List<PolarisEntityCore> catalogPath,
      PolarisEntityType entityType,
      PolarisEntitySubType entitySubType,
      String name) {
    return null;
  }

  @Override
  public ListEntitiesResult listEntities(
      PolarisCallContext callCtx,
      List<PolarisEntityCore> catalogPath,
      PolarisEntityType entityType,
      PolarisEntitySubType entitySubType) {
    return null;
  }

  @Override
  public GenerateEntityIdResult generateNewEntityId(PolarisCallContext callCtx) {
    return null;
  }

  @Override
  public CreatePrincipalResult createPrincipal(
      PolarisCallContext callCtx, PolarisBaseEntity principal) {
    return null;
  }

  @Override
  public CreateCatalogResult createCatalog(
      PolarisCallContext callCtx,
      PolarisBaseEntity catalog,
      List<PolarisEntityCore> principalRoles) {
    return null;
  }

  @Override
  public EntityResult createEntityIfNotExists(
      PolarisCallContext callCtx, List<PolarisEntityCore> catalogPath, PolarisBaseEntity entity) {
    return null;
  }

  @Override
  public EntitiesResult createEntitiesIfNotExist(
      PolarisCallContext callCtx,
      List<PolarisEntityCore> catalogPath,
      List<? extends PolarisBaseEntity> entities) {
    return null;
  }

  @Override
  public EntityResult updateEntityPropertiesIfNotChanged(
      PolarisCallContext callCtx, List<PolarisEntityCore> catalogPath, PolarisBaseEntity entity) {
    return null;
  }

  @Override
  public EntitiesResult updateEntitiesPropertiesIfNotChanged(
      PolarisCallContext callCtx, List<EntityWithPath> entities) {
    return null;
  }

  @Override
  public EntityResult renameEntity(
      PolarisCallContext callCtx,
      List<PolarisEntityCore> catalogPath,
      PolarisBaseEntity entityToRename,
      List<PolarisEntityCore> newCatalogPath,
      PolarisEntity renamedEntity) {
    return null;
  }

  @Override
  public DropEntityResult dropEntityIfExists(
      PolarisCallContext callCtx,
      List<PolarisEntityCore> catalogPath,
      PolarisBaseEntity entityToDrop,
      Map<String, String> cleanupProperties,
      boolean cleanup) {
    return null;
  }

  @Override
  public EntityResult loadEntity(
      PolarisCallContext callCtx,
      long entityCatalogId,
      long entityId,
      PolarisEntityType entityType) {
    return null;
  }

  @Override
  public EntitiesResult loadTasks(PolarisCallContext callCtx, String executorId, int limit) {
    return null;
  }

  @Override
  public ChangeTrackingResult loadEntitiesChangeTracking(
      PolarisCallContext callCtx, List<PolarisEntityId> entityIds) {
    return null;
  }

  @Override
  public ResolvedEntityResult loadResolvedEntityById(
      PolarisCallContext callCtx,
      long entityCatalogId,
      long entityId,
      PolarisEntityType entityType) {
    return nextResult();
  }

  @Override
  public ResolvedEntityResult loadResolvedEntityByName(
      PolarisCallContext callCtx,
      long entityCatalogId,
      long parentId,
      PolarisEntityType entityType,
      String entityName) {
    return nextResult();
  }

  @Override
  public ResolvedEntityResult refreshResolvedEntity(
      PolarisCallContext callCtx,
      int entityVersion,
      int entityGrantRecordsVersion,
      PolarisEntityType entityType,
      long entityCatalogId,
      long entityId) {
    return null;
  }

  @Override
  public PrivilegeResult grantUsageOnRoleToGrantee(
      PolarisCallContext callCtx,
      PolarisEntityCore catalog,
      PolarisEntityCore role,
      PolarisEntityCore grantee) {
    return null;
  }

  @Override
  public PrivilegeResult revokeUsageOnRoleFromGrantee(
      PolarisCallContext callCtx,
      PolarisEntityCore catalog,
      PolarisEntityCore role,
      PolarisEntityCore grantee) {
    return null;
  }

  @Override
  public PrivilegeResult grantPrivilegeOnSecurableToRole(
      PolarisCallContext callCtx,
      PolarisEntityCore grantee,
      List<PolarisEntityCore> catalogPath,
      PolarisEntityCore securable,
      PolarisPrivilege privilege) {
    return null;
  }

  @Override
  public PrivilegeResult revokePrivilegeOnSecurableFromRole(
      PolarisCallContext callCtx,
      PolarisEntityCore grantee,
      List<PolarisEntityCore> catalogPath,
      PolarisEntityCore securable,
      PolarisPrivilege privilege) {
    return null;
  }

  @Override
  public LoadGrantsResult loadGrantsOnSecurable(
      PolarisCallContext callCtx, PolarisEntityCore securable) {
    return null;
  }

  @Override
  public LoadGrantsResult loadGrantsToGrantee(
      PolarisCallContext callCtx, PolarisEntityCore grantee) {
    return null;
  }

  @Override
  public PrincipalSecretsResult loadPrincipalSecrets(PolarisCallContext callCtx, String clientId) {
    return null;
  }

  @Override
  public PrincipalSecretsResult rotatePrincipalSecrets(
      PolarisCallContext callCtx,
      String clientId,
      long principalId,
      boolean reset,
      String oldSecretHash) {
    return null;
  }

  @Override
  public ScopedCredentialsResult getSubscopedCredsForEntity(
      PolarisCallContext callCtx,
      long catalogId,
      long entityId,
      PolarisEntityType entityType,
      boolean allowListOperation,
      Set<String> allowedReadLocations,
      Set<String> allowedWriteLocations) {
    return null;
  }

  @Override
  public ValidateAccessResult validateAccessToLocations(
      PolarisCallContext callCtx,
      long catalogId,
      long entityId,
      PolarisEntityType entityType,
      Set<PolarisStorageActions> actions,
      Set<String> locations) {
    return null;
  }
}
