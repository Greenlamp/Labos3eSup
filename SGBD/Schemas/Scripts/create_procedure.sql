create or replace
procedure livraison
as
    type commander_t is table of commander%rowtype index by binary_integer;
    v_commander commander_t;
    v_idCopie integer_t;
    v_idComplexeCC1 integer;
    v_nb integer;
    v_count integer := 0;
    v_copie copies%rowtype;
    v_copieFound integer := 0;
begin
    --R�cup�ration de la liste des commandes � �ffectuer
    select * bulk collect into v_commander
    from commander;
    
    package_error.report_and_go('Nombre de commandes: ' || v_commander.count, -20001);
    --On parcourt cette liste de commandes
    for i in v_commander.first .. v_commander.last
    loop
        package_error.report_and_go('passage(i): '||i, -20001);
        --On r�cup�re toutes les copies qui corresponde � la copie I.
        select idCopie bulk collect into v_idCopie
        from copies
        where idMovie = v_commander(i).idmovie
        and physique = v_commander(i).physique;
        package_error.report_and_go('v_commander(i).idmovie: '|| v_commander(i).idmovie, -20001);
        
        package_error.report_and_go('v_idCopie.count: '|| v_idCopie.count, -20001);
        package_error.report_and_go('v_commander(i).nbCopie: '||v_commander(i).nbCopie, -20001);
        
        --On calcule le nombre de copie � transporter.
        --D'abord le nombre de copie � transporter c'est le nombre de copie demander dans la commande
        v_nb := v_commander(i).nbCopie;
        --On v�rifie si on poss�de assez de copies
        if(v_nb > v_idCopie.count) then
          --Si on ne poss�de pas assez de copie pour faire la commande compl�te, on prend tout ce qui reste de nos copies
          v_nb := v_idCopie.count;
        end if;
        package_error.report_and_go('v_nb: '|| v_nb, -20001);
        
        --Si le nombre de copies trouver dans la db est sup�rieur � 0, on fait le transport
        if(v_idCopie.count > 0) then
          --On r�cup�re l'id du complexe cc1
          select idComplexe into v_idComplexeCC1 from Complexe where nameComplexe = 'CC1';
            --Si la commande provient de cc1 on fait le transport
            if(v_commander(i).idComplexe = v_idComplexeCC1) then
              for j in 1 .. v_nb
              loop
              package_error.report_and_go('passage(j): '||j, -20001);
              package_error.report_and_go('v_copieFound: '||v_copieFound, -20001);
                select count(*) into v_copieFound from COPIES_CC1 where idCopie = v_idCopie(j) and deleted = '1';
                if(v_copieFound = 0) then
                    package_error.report_and_go('pas de suppression logique, on insert', -20001);
                    select * into v_copie from copies where idCopie = v_idCopie(j);
                    insert into COPIES_CC1(idCopie, physique, idMovie)
                    values(v_copie.idCopie, v_copie.physique, v_copie.idMovie);
                else
                    package_error.report_and_go('suppression logique, on update', -20001);
                    update COPIES_CC1 set deleted = '0'
                    where idCopie = v_idCopie(j) and deleted = '1';
                end if;
                delete from copies where idCopie = v_idCopie(j);
                v_count := v_count + 1;
              end loop;
              --package_error.report_and_go('v_count: '||v_count, -20001);
              if(v_count = v_commander(i).nbCopie) then
                delete from commander where idMovie = v_commander(i).idMovie and physique = v_commander(i).physique;
              else
                v_nb := v_commander(i).nbCopie - v_count;
                update commander set nbCopie = v_nb where idMovie = v_commander(i).idMovie and physique = v_commander(i).physique;  
              end if;
            end if;
        end if;
    end loop;
    commit;
    
    exception
        when others then raise;
end;