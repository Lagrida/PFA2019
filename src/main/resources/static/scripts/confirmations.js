function deleteDepartement(id){
	var msg = "Vous etes sur de supprimer ce département?";
	if(confirm(msg)){
		document.location.href = "deleteDepartement?id="+id;
	}
}
function deleteTypeStage(id){
	var msg = "Vous etes sur de supprimer ce type de stage?";
	if(confirm(msg)){
		document.location.href = "deleteTypeStage?id="+id;
	}
}
function deleteUser(id){
	var msg = "Vous etes sur de supprimer ce membre?";
	if(confirm(msg)){
		document.location.href = "deleteUser?id="+id;
	}
}
function demandeStageApprouve(id){
	var msg = "Vous etes sur d'accepter cette demande?";
	if(confirm(msg)){
		document.location.href = "actionDemande?type=approuver&id="+id;
	}
}
function demandeStageRefus(id){
	var msg = "Vous etes sur de refuser cette demande?";
	if(confirm(msg)){
		document.location.href = "actionDemande?type=refuser&id="+id;
	}
}
function deleteFile(id){
	var msg = "Vous etes sur de supprimer ce fichier?";
	if(confirm(msg)){
		document.location.href = "deleteFile?id="+id;
	}
}